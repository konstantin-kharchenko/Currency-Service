package by.kharchenko.auth.service.impl;

import by.kharchenko.auth.dto.*;
import by.kharchenko.auth.dto.Tokens;
import by.kharchenko.auth.model.User;
import by.kharchenko.auth.repository.RoleRepository;
import by.kharchenko.auth.repository.UserRepository;
import by.kharchenko.auth.security.JwtTokenProvider;
import by.kharchenko.auth.security.JwtType;
import by.kharchenko.auth.service.OAuthAppInfo;
import by.kharchenko.auth.service.TokenService;
import by.kharchenko.auth.util.encoder.CustomPasswordEncoder;
import by.kharchenko.auth.util.jwt.JwtUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.jsonwebtoken.Claims;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider tokenProvider;
    private final Cache<Long, List<Tokens>> cache;
    private final OAuthAppInfo oAuthAppInfo;

    public TokenServiceImpl(UserRepository userRepository
            , RoleRepository roleRepository, JwtTokenProvider tokenProvider, OAuthAppInfo oAuthAppInfo) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.oAuthAppInfo = oAuthAppInfo;
        this.cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public Tokens signIn(AuthUserDto authUserDto) throws Exception {
        var optionalUser = userRepository.findByUsername(authUserDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(CustomPasswordEncoder.encode(authUserDto.getPassword()))) {

                String accessToken = tokenProvider.createAccessToken(user);
                String refreshToken = tokenProvider.createRefreshToken(user);

                Tokens tokens = new Tokens(accessToken, refreshToken);

                List<Tokens> tokensList = cache.get(user.getId(), () -> {
                    return new ArrayList<Tokens>();
                });

                tokensList.add(tokens);

                return tokens;
            } else {
                throw new Exception("invalid password");
            }
        } else {
            throw new Exception("username not found");
        }
    }

    @Override
    public JwtAuthenticationDto check(String accessToken) throws ExecutionException {
        boolean check = tokenProvider.validateToken(accessToken, JwtType.ACCESS);
        if (check) {

            Claims claims = tokenProvider.getAccessClaims(accessToken);
            Long id = Long.parseLong(claims.getSubject());

            if (cache.get(id, () -> {
                return new ArrayList();
            }).size() == 0) {
                return null;
            }

            JwtAuthenticationDto authentication = JwtUtils.generate(tokenProvider.getAccessClaims(accessToken));
            return authentication;
        } else {
            return null;
        }
    }

    @Override
    public Tokens refreshToken(String refreshToken) throws Exception {
        boolean check = tokenProvider.validateToken(refreshToken, JwtType.REFRESH);
        if (check) {


            Claims claims = tokenProvider.getRefreshClaims(refreshToken);
            Long id = Long.parseLong(claims.getSubject());
            User user = userRepository.findById(id).get();

            List<Tokens> tokensList = cache.get(user.getId(), () -> {
                return new ArrayList<Tokens>();
            });
            if (tokensList.size() == 0) {
                throw new Exception("Your logins invalid");
            }

            String accessToken = tokenProvider.createAccessToken(user);
            String newRefreshToken = tokenProvider.createRefreshToken(user);

            Tokens tokens = new Tokens(accessToken, refreshToken);

            for (Tokens tokens1 : tokensList) {
                if (tokens1.getRefreshToken().equals(refreshToken)) {
                    tokens1.setRefreshToken(newRefreshToken);
                    tokens1.setAccessToken(accessToken);
                    break;
                }
            }

            return tokens;
        } else {
            throw new Exception("refresh token is not valid");
        }

    }

    @Override
    public void fullLogout(String id) {
        cache.invalidate(id);
    }

    @Override
    public CodeUser signInFromGitHub(Map<String, String> params, String state) throws ExecutionException {
        String gitState = params.get("state");
        System.out.println("gitState: " + gitState);
        if (gitState.equals(state)) {
            RestTemplate restTemplate = new RestTemplate();
            String accessTokenUrl = "https://github.com/login/oauth/access_token";
            CodeToTokenEntity codeToTokenEntity = new CodeToTokenEntity(
                    oAuthAppInfo.gitHubClientId
                    , oAuthAppInfo.gitHubClientSecret
                    , params.get("code"));

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<AccessTokenGitHubEntity> requestAccessToken = new HttpEntity(codeToTokenEntity, headers);
            ResponseEntity<AccessTokenGitHubEntity> responseAccessToken = restTemplate
                    .exchange(accessTokenUrl, HttpMethod.POST, requestAccessToken, AccessTokenGitHubEntity.class);

            restTemplate = new RestTemplate();
            headers.set("Authorization", "Bearer " + responseAccessToken.getBody().getAccess_token());
            String userUrl = "https://api.github.com/user";
            HttpEntity<GitHubUser> requestUser = new HttpEntity(headers);
            ResponseEntity<GitHubUser> responseUser = restTemplate.exchange(
                    userUrl, HttpMethod.GET, requestUser, GitHubUser.class);

            GitHubUser gitHubUser = responseUser.getBody();

            Optional<User> optionalUser = userRepository.findByGitHubId(gitHubUser.getId());
            String code = null;
            if (!optionalUser.isPresent()) {
                System.out.println("register");
                String registerUrl = oAuthAppInfo.gitHubUri;
                restTemplate = new RestTemplate();
                HttpEntity<ResultUserAfterRegisterDto> requestRegister = new HttpEntity(gitHubUser);

                ResponseEntity<ResultUserAfterRegisterDto> responseRegister = restTemplate
                        .exchange(registerUrl, HttpMethod.POST, requestRegister, ResultUserAfterRegisterDto.class);

                code = UUID.randomUUID().toString();
                return new CodeUser(code, responseRegister.getBody().getId());
            }
            code = UUID.randomUUID().toString();
            return new CodeUser(code, optionalUser.get().getId());
        }
        return null;

    }

    @Override
    public Tokens getTokensByCode(Long userId, String secret, String id) throws Exception {

        if (secret.equals(oAuthAppInfo.clientSecret) && id.equals(oAuthAppInfo.clientId)) {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                String accessToken = tokenProvider.createAccessToken(user);
                String refreshToken = tokenProvider.createRefreshToken(user);

                Tokens tokens = new Tokens(accessToken, refreshToken);

                List<Tokens> tokensList = cache.get(user.getId(), () -> {
                    return new ArrayList();
                });

                tokensList.add(tokens);

                return tokens;

            } else {
                throw new Exception("Invalid code");
            }
        } else {
            throw new Exception("Invalid client_id or client_secret");
        }
    }

    @Override
    public CodeUser signInFromFaceBook(Map<String, String> params, String state) {
        String gitState = params.get("state");
        if (gitState.equals(state)) {
            RestTemplate restTemplate = new RestTemplate();
            String tokenUrl = "https://graph.facebook.com/oauth/access_token" +
                    "?client_id=" + oAuthAppInfo.faceBookClientId +
                    "&client_secret=" + oAuthAppInfo.faceBookClientSecret +
                    "&grant_type=authorization_code" +
                    "&redirect_uri=http://localhost:9000/auth/from-facebook" +
                    "&code=" + params.get("code");
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<AccessTokenFaceBookEntity> tokenRequest = new HttpEntity(headers);
            ResponseEntity<AccessTokenFaceBookEntity> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.GET, tokenRequest, AccessTokenFaceBookEntity.class);

            restTemplate = new RestTemplate();
            String userUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + tokenResponse.getBody().getAccess_token();

            HttpEntity<FaceBookUser> userRequest = new HttpEntity(headers);
            ResponseEntity<FaceBookUser> userResponse = restTemplate.exchange(userUrl, HttpMethod.GET, userRequest, FaceBookUser.class);
            FaceBookUser faceBookUser = userResponse.getBody();

            Optional<User> optionalUser = userRepository.findByFaceBookId(faceBookUser.getId());
            String code = null;
            if (!optionalUser.isPresent()) {
                String registerUrl = oAuthAppInfo.faceBookUri;
                restTemplate = new RestTemplate();
                HttpEntity<ResultUserAfterRegisterDto> requestRegister = new HttpEntity(faceBookUser);

                ResponseEntity<ResultUserAfterRegisterDto> responseRegister = restTemplate
                        .exchange(registerUrl, HttpMethod.POST, requestRegister, ResultUserAfterRegisterDto.class);

                code = UUID.randomUUID().toString();
                return new CodeUser(code, responseRegister.getBody().getId());
            }
            code = UUID.randomUUID().toString();
            return new CodeUser(code, optionalUser.get().getId());
        }
        return null;
    }
}

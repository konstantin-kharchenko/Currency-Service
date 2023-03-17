package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.AuthUserDto;
import by.kharchenko.auth.dto.AccessTokenDto;
import by.kharchenko.auth.dto.Tokens;
import by.kharchenko.auth.dto.JwtAuthenticationDto;
import by.kharchenko.auth.exception.InvalidUsernameOrPasswordException;
import by.kharchenko.auth.service.impl.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class AuthController extends AbstractController {
    private static final String ACCESS_TOKEN = "Access-Token";

    private final TokenServiceImpl tokenService;

    @PostMapping("/")
    public ResponseEntity<AccessTokenDto> auth(@Valid @RequestBody AuthUserDto user, HttpServletResponse response) throws InvalidUsernameOrPasswordException, ExecutionException {
        Tokens tokens = tokenService.signIn(user);
        putRefreshTokenInCookie(tokens.getRefreshToken(), response);
        return ResponseEntity.ok(new AccessTokenDto(tokens.getAccessToken()));
    }

    @GetMapping("/check")
    public ResponseEntity<JwtAuthenticationDto> checkAccessToken(HttpServletRequest request) throws ExecutionException {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        return ResponseEntity.ok(tokenService.check(accessToken));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(@CookieValue("refresh-token") String refreshToken, HttpServletResponse response) throws Exception {
        Tokens tokens = tokenService.refreshToken(refreshToken);
        putRefreshTokenInCookie(tokens.getRefreshToken(), response);
        return ResponseEntity.ok(new AccessTokenDto(tokens.getAccessToken()));
    }

    @GetMapping("/full-logout/{id}")
    public void fullLogout(@PathVariable("id") String id) {
        tokenService.fullLogout(id);
    }
}

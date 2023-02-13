package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.AccessTokenDto;
import by.kharchenko.auth.dto.Tokens;
import by.kharchenko.auth.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CodeController extends AbstractController {

    private final TokenService tokenService;

    @GetMapping("/code")
    public ResponseEntity<AccessTokenDto> getToken(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String code = params.get("code");
        String clientId = params.get("client_id");
        String clientSecret = params.get("client_secret");

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(code)) {
                Tokens tokens = tokenService.getTokensByCode(Long.parseLong(cookie.getValue()), clientSecret, clientId);
                putRefreshTokenInCookie(tokens.getRefreshToken(), response);
                return ResponseEntity.ok(new AccessTokenDto(tokens.getAccessToken()));
            }
        }

        return null;
    }
}

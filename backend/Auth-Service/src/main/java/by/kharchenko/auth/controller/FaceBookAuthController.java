package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.CodeUser;
import by.kharchenko.auth.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
public class FaceBookAuthController extends AbstractController {

    private final TokenService tokenService;

    @GetMapping("/to-facebook")
    public void redirectToFaceBook(HttpServletResponse response, @Value("${facebook.client-id}") String clientId) throws IOException {
        String state = UUID.randomUUID().toString();
        putStateInCookie(state, response);
        String redirectUrl = "https://www.facebook.com/v15.0/dialog/oauth?client_id="
                + clientId
                + "&state="
                + state
                + "&redirect_uri=http://localhost:8080/auth/from-facebook";
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/from-facebook")
    public void redirectFromFaceBook(HttpServletResponse response
            , @RequestParam Map<String, String> params
            , @CookieValue("state") String state) throws IOException, ExecutionException {
        CodeUser codeUser = tokenService.signInFromFaceBook(params, state);
        putCodeInCookie(codeUser, response);
        deleteStateFromCookie(response);
        response.sendRedirect("http://localhost:3000/login?code=" + codeUser.getCode());
    }
}

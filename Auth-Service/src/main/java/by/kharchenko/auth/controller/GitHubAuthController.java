package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.CodeUser;
import by.kharchenko.auth.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
public class GitHubAuthController extends AbstractController {

    private final TokenService tokenService;

    @GetMapping("/to-github")
    public void redirectToGitHub(HttpServletResponse response, @Value("${github.client-id}") String clientId) throws IOException {
        String state = UUID.randomUUID().toString();
        putStateInCookie(state, response);
        String redirectUrl = "https://github.com/login/oauth/authorize?response_type=code&client_id="
                + clientId
                + "&scope=read:user&state="
                + state
                + "&redirect_uri=http://localhost:8080/auth/from-github";
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/from-github")
    public void redirectFromGitHub(HttpServletResponse response
            , @RequestParam Map<String, String> params
            , @CookieValue("state") String state) throws IOException, ExecutionException {
        CodeUser codeUser = tokenService.signInFromGitHub(params, state);
        putCodeInCookie(codeUser, response);
        response.sendRedirect("http://localhost:3000/login?code=" + codeUser.getCode());
    }

}

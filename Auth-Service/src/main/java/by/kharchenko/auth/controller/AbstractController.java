package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.CodeUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public abstract class AbstractController {


    protected void putStateInCookie(String state, HttpServletResponse response) {
        Cookie cookie = new Cookie("state", state);
        cookie.setMaxAge(20000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        response.addCookie(cookie);
    }

    protected void putCodeInCookie(CodeUser codeUser, HttpServletResponse response) {
        Cookie cookie = new Cookie(codeUser.getCode(), codeUser.getId().toString());
        cookie.setMaxAge(20000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        response.addCookie(cookie);
    }


    protected void putRefreshTokenInCookie(String refresh, HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh-token", refresh);
        cookie.setMaxAge(1800);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        response.addCookie(cookie);
    }
}

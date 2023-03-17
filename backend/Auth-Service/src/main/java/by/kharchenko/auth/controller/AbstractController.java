package by.kharchenko.auth.controller;

import by.kharchenko.auth.dto.CodeUser;
import by.kharchenko.auth.dto.ExceptionMessageDto;
import by.kharchenko.auth.exception.InvalidUsernameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public abstract class AbstractController {


    protected void putStateInCookie(String state, HttpServletResponse response) {
        Cookie cookie = new Cookie("state", state);
        cookie.setMaxAge(20000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);

        response.addCookie(cookie);
    }

    protected void putCodeInCookie(CodeUser codeUser, HttpServletResponse response) {
        Cookie cookie = new Cookie(codeUser.getCode(), codeUser.getId().toString());
        cookie.setMaxAge(20000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);

        response.addCookie(cookie);
    }


    protected void putRefreshTokenInCookie(String refresh, HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh-token", refresh);
        cookie.setMaxAge(200000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(true);

        response.addCookie(cookie);
    }

    protected void deleteCodeFromCookie(String code, HttpServletResponse response) {
        Cookie cookie = new Cookie(code, null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    protected void deleteStateFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("state", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ExceptionMessageDto> handleAccountNumberNotExistsException(InvalidUsernameOrPasswordException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Bad username or password", ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionMessageDto(1L, errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessageDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new ExceptionMessageDto(2L, errors));
    }
}

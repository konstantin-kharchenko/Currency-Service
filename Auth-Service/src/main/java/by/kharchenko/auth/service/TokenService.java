package by.kharchenko.auth.service;

import by.kharchenko.auth.dto.AuthUserDto;
import by.kharchenko.auth.dto.CodeUser;
import by.kharchenko.auth.dto.Tokens;
import by.kharchenko.auth.dto.JwtAuthenticationDto;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface TokenService {

    Tokens signIn(AuthUserDto authUserDto) throws Exception;

    JwtAuthenticationDto check(String accessToken) throws ExecutionException;

    Tokens refreshToken(String refreshToken) throws Exception;

    void fullLogout(String id);

    CodeUser signInFromGitHub(Map<String, String> params, String state) throws ExecutionException;

    Tokens getTokensByCode(Long userId, String secret, String id) throws Exception;

    CodeUser signInFromFaceBook(Map<String, String> params, String state);
}

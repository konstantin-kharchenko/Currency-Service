package by.kharchenko.registration.service;

import by.kharchenko.registration.dto.FaceBookUser;
import by.kharchenko.registration.dto.GitHubUser;
import by.kharchenko.registration.dto.RegisterUserDto;
import by.kharchenko.registration.dto.ResultUserAfterRegisterDto;
import by.kharchenko.registration.exception.UsernameAlreadyExistsException;

import java.util.Map;

public interface UserService {

    void add(RegisterUserDto userDto) throws UsernameAlreadyExistsException;

    ResultUserAfterRegisterDto addFromGitHub(GitHubUser gitHubUser);

    ResultUserAfterRegisterDto addFromFaceBook(FaceBookUser faceBookUser);
}

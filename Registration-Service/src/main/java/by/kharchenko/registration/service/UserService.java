package by.kharchenko.registration.service;

import by.kharchenko.registration.dto.FaceBookUser;
import by.kharchenko.registration.dto.GitHubUser;
import by.kharchenko.registration.dto.RegisterUserDto;
import by.kharchenko.registration.dto.ResultUserAfterRegisterDto;

import java.util.Map;

public interface UserService {

    void add(RegisterUserDto userDto);

    ResultUserAfterRegisterDto addFromGitHub(GitHubUser gitHubUser);

    ResultUserAfterRegisterDto addFromFaceBook(FaceBookUser faceBookUser);
}

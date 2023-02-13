package by.kharchenko.registration.controller;

import by.kharchenko.registration.dto.FaceBookUser;
import by.kharchenko.registration.dto.GitHubUser;
import by.kharchenko.registration.dto.RegisterUserDto;
import by.kharchenko.registration.dto.ResultUserAfterRegisterDto;
import by.kharchenko.registration.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegisterController {

    private final UserService userService;

    @PostMapping("/")
    public void add(@RequestBody RegisterUserDto userDto) {
        userService.add(userDto);
    }

    @PostMapping("/github")
    public ResponseEntity<ResultUserAfterRegisterDto> addFromGitHub(@RequestBody GitHubUser gitHubUser) {
        ResultUserAfterRegisterDto registerDto = userService.addFromGitHub(gitHubUser);
        return ResponseEntity.ok(registerDto);
    }

    @PostMapping("/facebook")
    public ResponseEntity<ResultUserAfterRegisterDto> addFromFaceBook(@RequestBody FaceBookUser faceBookUser) {
        ResultUserAfterRegisterDto registerDto = userService.addFromFaceBook(faceBookUser);
        return ResponseEntity.ok(registerDto);
    }


}

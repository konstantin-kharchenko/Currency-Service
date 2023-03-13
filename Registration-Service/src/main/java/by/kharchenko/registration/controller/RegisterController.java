package by.kharchenko.registration.controller;

import by.kharchenko.registration.dto.*;
import by.kharchenko.registration.exception.UsernameAlreadyExistsException;
import by.kharchenko.registration.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegisterController {

    private final UserService userService;

    @PostMapping("/")
    public void add(@Valid @RequestBody RegisterUserDto userDto) throws UsernameAlreadyExistsException {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessageDto> handleAccountNumberNotExistsException(UsernameAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Username already exists", ex.getMessage());
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

package by.kharchenko.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid username")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_=-])[A-Za-z\\d_=-]{8,}$", message = "Invalid password")
    private String password;
}

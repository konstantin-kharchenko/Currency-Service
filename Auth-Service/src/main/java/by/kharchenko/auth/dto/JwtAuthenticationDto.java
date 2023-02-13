package by.kharchenko.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JwtAuthenticationDto {

    private boolean authenticated;
    private Long userId;
    private Set<String> authorities;
}
package by.kharchenko.history.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JwtAuthorizationDto {
    private boolean authenticated;
    private Long userId;
    private Set<String> authorities;
}

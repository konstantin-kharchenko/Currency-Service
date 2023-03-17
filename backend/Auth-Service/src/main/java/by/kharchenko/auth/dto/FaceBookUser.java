package by.kharchenko.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaceBookUser {
    private Long id;
    private String name;
    private String email;
}

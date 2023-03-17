package by.kharchenko.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionMessageDto {
    private Long id;
    Map<String, String> messages;
}

package by.kharchenko.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessageDto {
    private Long id;
    private String message;
}

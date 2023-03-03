package by.kharchenko.processing.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class TransferAccountDto {

    @NotEmpty
    private String fromAccount;

    @NotEmpty
    private String toAccount;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal count;
}

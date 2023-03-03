package by.kharchenko.processing.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class AddCountDto {

    @NotEmpty
    private String accountNumber;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal moneyCount;

}

package by.kharchenko.processing.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class AccountDto {
    private String accountNumber;
    private String currency;
    private BigDecimal moneyCount;
}

package by.kharchenko.processing.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferAccountDto {
    private String fromAccount;
    private String toAccount;
    private BigDecimal count;
}

package by.kharchenko.processing.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddCountDto {

    private String accountNumber;
    private BigDecimal moneyCount;

}

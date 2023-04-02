package by.kharchenko.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrenciesDto {
    private BigDecimal usd;
    private BigDecimal eur;
    private BigDecimal gbp;
}

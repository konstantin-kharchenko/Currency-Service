package by.kharchenko.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrenciesDto {
    private Double usd;
    private Double eur;
    private Double gbp;
}

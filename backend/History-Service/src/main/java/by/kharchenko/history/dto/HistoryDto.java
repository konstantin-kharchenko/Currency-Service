package by.kharchenko.history.dto;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HistoryDto {
    private String fromCurrency;

    private String toCurrency;

    private BigDecimal fromAmount;

    private BigDecimal toAmount;

    private Date created;

    private String fromAccountNumber;

    private String toAccountNumber;

    private String actionType;
}

package by.kharchenko.processing.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AccountEvent {
    private String uuid;
    private Long userId;
    private Long accountId;
    private String currency;
    private BigDecimal amount;
    private Date created;
}

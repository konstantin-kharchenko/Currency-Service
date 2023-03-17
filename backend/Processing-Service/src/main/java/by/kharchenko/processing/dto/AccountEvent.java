package by.kharchenko.processing.dto;

import by.kharchenko.processing.entity.ActionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
public class AccountEvent {
    private Long userId;
    private Long fromAccountId;
    private Long toAccountId;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal fromAmount;
    private BigDecimal toAmount;
    private Date created;
    private ActionType action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEvent that = (AccountEvent) o;
        return Objects.equals(userId, that.userId) && Objects.equals(fromAccountId, that.fromAccountId) && Objects.equals(toAccountId, that.toAccountId) && Objects.equals(fromCurrency, that.fromCurrency) && Objects.equals(toCurrency, that.toCurrency) && Objects.equals(toAmount, that.toAmount) && Objects.equals(fromAmount, that.fromAmount) && Objects.equals(created, that.created) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fromAccountId, toAccountId, fromCurrency, toCurrency, fromAmount, toAmount, created, action);
    }

    @Override
    public String toString() {
        return "AccountEvent{" +
                "userId=" + userId +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", fromAmount=" + fromAmount +
                ", toAmount=" + toAmount +
                ", created=" + created +
                ", action='" + action + '\'' +
                '}';
    }
}

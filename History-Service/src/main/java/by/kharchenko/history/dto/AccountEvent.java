package by.kharchenko.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class AccountEvent {
    private String uuid;
    private Long userId;
    private Long accountId;
    private String currency;
    private BigDecimal amount;
    private Date created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEvent that = (AccountEvent) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(userId, that.userId) && Objects.equals(accountId, that.accountId) && Objects.equals(currency, that.currency) && Objects.equals(amount, that.amount) && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userId, accountId, currency, amount, created);
    }

    @Override
    public String toString() {
        return "AccountEvent{" +
                "uuid='" + uuid + '\'' +
                ", userId=" + userId +
                ", accountId=" + accountId +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", created=" + created +
                '}';
    }
}

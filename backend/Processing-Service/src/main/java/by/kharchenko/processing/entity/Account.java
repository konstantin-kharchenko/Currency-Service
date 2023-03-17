package by.kharchenko.processing.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "currency")
    private String currency;

    @Column(name = "money_count")
    private BigDecimal moneyCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", moneyCount=" + moneyCount +
                '}';
    }
}

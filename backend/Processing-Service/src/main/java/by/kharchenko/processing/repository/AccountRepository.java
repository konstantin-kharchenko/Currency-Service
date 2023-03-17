package by.kharchenko.processing.repository;

import by.kharchenko.processing.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String fromAccountNumber);

    @Modifying
    @Query("delete from Account a where a.accountNumber = ?1")
    void deleteByAccountNumber(String accountNumber);

    @Query(value = "select * from accounts a where a.user_id = ?1", nativeQuery = true)
    Page<Account> findByUserId(Long id, Pageable pageable);

    @Query(value = "select * from accounts a where a.user_id = ?1 and a.currency = ?2", nativeQuery = true)
    Page<Account> findByUserIdAndCurrency(Long id, String currency, Pageable pageable);
}

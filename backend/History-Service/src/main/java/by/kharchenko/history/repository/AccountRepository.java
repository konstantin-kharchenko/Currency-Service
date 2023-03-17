package by.kharchenko.history.repository;

import by.kharchenko.history.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String fromAccountNumber);

    void deleteByAccountNumber(String accountNumber);

    @Query(value = "select * from accounts a where a.user_id = ?1", nativeQuery = true)
    List<Account> findByUserId(Long id);
}

package by.kharchenko.processing.repository;

import by.kharchenko.processing.entity.Account;
import by.kharchenko.processing.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Modifying
    @Query("delete from History h where h.toAccount = ?1 or h.fromAccount = ?1")
    void deleteByAccount(Account account);
}
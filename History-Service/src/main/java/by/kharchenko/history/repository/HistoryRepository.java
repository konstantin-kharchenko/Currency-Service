package by.kharchenko.history.repository;

import by.kharchenko.history.entity.Account;
import by.kharchenko.history.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("from History h where h.fromAccount = ?1 or h.toAccount = ?1")
    Page<History> findByAccount(Account account, Pageable pageable);
}
package by.kharchenko.history.repository;

import by.kharchenko.history.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

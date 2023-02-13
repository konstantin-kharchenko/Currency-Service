package by.kharchenko.processing.repository;

import by.kharchenko.processing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

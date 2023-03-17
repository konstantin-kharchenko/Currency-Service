package by.kharchenko.auth.repository;

import by.kharchenko.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByGitHubId(Long id);

    Optional<User> findByFaceBookId(Long id);
}

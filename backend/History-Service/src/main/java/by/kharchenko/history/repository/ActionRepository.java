package by.kharchenko.history.repository;

import by.kharchenko.history.entity.Action;
import by.kharchenko.history.entity.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findActionByActionType(ActionType actionType);
}

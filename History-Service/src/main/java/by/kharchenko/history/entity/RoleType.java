package by.kharchenko.history.entity;

import lombok.Getter;

public enum RoleType {
    ROLE_ADMINISTRATOR("ADMINISTRATOR"),
    ROLE_USER("USER");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}

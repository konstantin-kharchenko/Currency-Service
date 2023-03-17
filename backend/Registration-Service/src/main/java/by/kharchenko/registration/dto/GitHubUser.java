package by.kharchenko.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitHubUser {
    private Long id;
    private String login;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubUser that = (GitHubUser) o;
        return Objects.equals(id, that.id) && Objects.equals(login, that.login) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email);
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

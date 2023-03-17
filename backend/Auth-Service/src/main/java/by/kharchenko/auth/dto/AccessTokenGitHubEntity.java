package by.kharchenko.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenGitHubEntity {
    private String access_token;
    private String scope;
    private String token_type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessTokenGitHubEntity that = (AccessTokenGitHubEntity) o;
        return Objects.equals(access_token, that.access_token) && Objects.equals(scope, that.scope) && Objects.equals(token_type, that.token_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, scope, token_type);
    }

    @Override
    public String toString() {
        return "AccessTokenGitHubEntity{" +
                "access_token='" + access_token + '\'' +
                ", scope='" + scope + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }
}

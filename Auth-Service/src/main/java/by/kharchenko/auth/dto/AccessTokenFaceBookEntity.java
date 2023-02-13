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
public class AccessTokenFaceBookEntity {
    private String access_token;
    private String token_type;
    private String expires_in;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessTokenFaceBookEntity that = (AccessTokenFaceBookEntity) o;
        return Objects.equals(access_token, that.access_token) && Objects.equals(token_type, that.token_type) && Objects.equals(expires_in, that.expires_in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, token_type, expires_in);
    }

    @Override
    public String toString() {
        return "AccessTokenFaceBookEntity{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}

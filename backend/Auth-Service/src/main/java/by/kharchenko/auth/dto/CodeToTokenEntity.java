package by.kharchenko.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class CodeToTokenEntity {
    private String client_id;
    private String client_secret;
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeToTokenEntity that = (CodeToTokenEntity) o;
        return Objects.equals(client_id, that.client_id) && Objects.equals(client_secret, that.client_secret) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, client_secret, code);
    }

    @Override
    public String toString() {
        return "CodeToTokenEntity{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

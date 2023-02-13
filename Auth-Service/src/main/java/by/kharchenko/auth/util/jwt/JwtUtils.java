package by.kharchenko.auth.util.jwt;

import by.kharchenko.auth.dto.JwtAuthenticationDto;
import io.jsonwebtoken.Claims;

import java.util.*;

public class JwtUtils {
    public static JwtAuthenticationDto generate(Claims claims) {
        final JwtAuthenticationDto jwtInfoToken = new JwtAuthenticationDto();
        jwtInfoToken.setUserId(Long.parseLong(claims.getSubject()));
        Set<String> authorities = getRoles(claims);
        jwtInfoToken.setAuthorities(authorities);
        jwtInfoToken.setAuthenticated(true);
        return jwtInfoToken;
    }

    private static Set<String> getRoles(Claims claims) {
        Set<String> resultRoles = new HashSet<>();
        final List<LinkedHashMap<Object, Object>> roles = claims.get("roles", List.class);
        for (LinkedHashMap<Object, Object> roleInfo : roles) {
            for (Map.Entry<Object, Object> entry : roleInfo.entrySet()) {
                if (entry.getKey().equals("role")) {
                    resultRoles.add(entry.getValue().toString());
                }
            }
        }

        return resultRoles;
    }
}

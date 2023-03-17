package by.kharchenko.processing.security;

import by.kharchenko.processing.dto.JwtAuthorizationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private Long userId;
    private Set<SimpleGrantedAuthority> authorities;

    public JwtAuthentication(JwtAuthorizationDto jwtAuthorizationDto) {
        this.authenticated = jwtAuthorizationDto.isAuthenticated();
        this.userId = jwtAuthorizationDto.getUserId();
        authorities = new HashSet<SimpleGrantedAuthority>();
        jwtAuthorizationDto.getAuthorities().forEach(a->authorities.add(new SimpleGrantedAuthority(a)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
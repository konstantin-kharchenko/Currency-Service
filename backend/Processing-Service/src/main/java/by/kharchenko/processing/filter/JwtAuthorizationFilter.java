package by.kharchenko.processing.filter;

import by.kharchenko.processing.dto.JwtAuthorizationDto;
import by.kharchenko.processing.security.JwtAuthentication;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String ACCESS_TOKEN = "Access-Token";
    private String authUri;

    public JwtAuthorizationFilter(String authUri, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authUri = authUri;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken != null) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(ACCESS_TOKEN, accessToken);
            HttpEntity<JwtAuthorizationDto> securityRequest = new HttpEntity(headers);
            ResponseEntity<JwtAuthorizationDto> responseEntity = restTemplate.exchange(authUri, HttpMethod.GET, securityRequest, JwtAuthorizationDto.class);
            try {
                JwtAuthentication jwtAuthentication = new JwtAuthentication(responseEntity.getBody());
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        chain.doFilter(request, response);
    }
}

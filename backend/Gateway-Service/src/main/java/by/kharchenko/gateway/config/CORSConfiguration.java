package by.kharchenko.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CORSConfiguration implements WebFluxConfigurer {

    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN, access-token, Set-Cookie";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";
    private static final String ALLOWED_ORIGIN = "http://localhost:3000";
    private static final String MAX_AGE = "7200"; //2 hours (2 * 60 * 60)
    private static final String URI_WITHOUT_ORIGIN = "/currency/main-currencies/info";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                if (!request.getURI().getPath().equals(URI_WITHOUT_ORIGIN)) {
                    headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                    headers.add("Access-Control-Allow-Credentials", "true");
                }
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", MAX_AGE); //OPTION how long the results of a preflight request (that is the information contained in the Access-Control-Allow-Methods and Access-Control-Allow-Headers headers) can be cached.
                headers.add("Access-Control-Allow-Headers",ALLOWED_HEADERS);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
}
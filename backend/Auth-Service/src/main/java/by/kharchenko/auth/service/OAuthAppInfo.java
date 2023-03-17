package by.kharchenko.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class OAuthAppInfo {
    public final String gitHubClientId;
    public final String gitHubClientSecret;
    public final String faceBookClientId;
    public final String faceBookClientSecret;
    public final String clientId;
    public final String clientSecret;
    public final String gitHubUri;
    public final String faceBookUri;

    public OAuthAppInfo(@Value("${github.client-id}") String gitHubClientId
            , @Value("${github.client-secret}") String gitHubClientSecret
            , @Value("${facebook.client-id}") String faceBookClientId
            , @Value("${facebook.client-secret}") String faceBookClientSecret
            , @Value("${code.client-id}") String clientId
            , @Value("${code.client-secret}") String clientSecret
            , @Value("${github.register.uri}")String gitHubUri
            ,@Value("${facebook.register.uri}") String faceBookUri) {
        this.gitHubClientId = gitHubClientId;
        this.gitHubClientSecret = gitHubClientSecret;
        this.faceBookClientId = faceBookClientId;
        this.faceBookClientSecret = faceBookClientSecret;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.gitHubUri = gitHubUri;
        this.faceBookUri = faceBookUri;
    }
}

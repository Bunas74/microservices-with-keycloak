package com.itm.space.backendresources.configuration;

import static org.keycloak.OAuth2Constants.*;
import org.keycloak.admin.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
public class KeycloakClientConfiguration {

    @Value("${keycloak.credentials.secret}")
    private String secretKey;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm(realm)
                .grantType(CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(secretKey)
                .build();
    }
}
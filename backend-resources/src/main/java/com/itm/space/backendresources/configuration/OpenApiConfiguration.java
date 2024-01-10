package com.itm.space.backendresources.configuration;

import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.*;
import org.springframework.context.annotation.*;

@Configuration
@SecurityScheme(
        name = "oauth2_auth_code",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://backend-keycloak-auth:8080/auth/realms/ITM/protocol/openid-connect/auth",
                        tokenUrl = "http://backend-keycloak-auth:8080/auth/realms/ITM/protocol/openid-connect/token",
                        scopes = {
                                @OAuthScope(name = "openid", description = "Read access")
                        }
                )
        ),
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI();
    }
}
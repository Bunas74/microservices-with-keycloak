package com.itm.space.backendresources;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFile("keycloak/ITM-realm.json")
            .withContextPath("/auth");

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/ITM");
        registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
    }

    protected String getBearerToken() {
        try (Keycloak keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .grantType("password")
                .realm("ITM")
                .scope("openid")
                .clientId("backend-gateway-client")
                .clientSecret("u5rzPiRQloXoPWAXBp9RRggn6fvp0KMk")
                .username("user")
                .password("user")
                .build()) {

            String token = keycloakAdminClient.tokenManager().getAccessToken().getToken();

            return "Bearer " + token;
        } catch (Exception e) {
            log.error("Can't obtain an access token from Keycloak!", e);
        }
        return null;
    }
}
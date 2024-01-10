package com.itm.space.backendresources.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(SecurityConfiguration::convertJwtToken)));
        return http.build();
    }

    private static JwtAuthenticationToken convertJwtToken(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwt, authorities);

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        List<?> rolesObject = Optional.ofNullable(realmAccess.get("roles"))
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .orElseGet(ArrayList::new);

        List<String> roles = rolesObject.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();

        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        return new JwtAuthenticationToken(jwt, authorities, authenticationToken.getName());
    }
}
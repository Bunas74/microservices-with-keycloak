package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.api.request.*;
import com.itm.space.backendresources.api.response.*;
import com.itm.space.backendresources.service.*;
import io.swagger.v3.oas.annotations.security.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.security.access.annotation.*;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Secured("ROLE_MODERATOR")
    @SecurityRequirement(name = "oauth2_auth_code")
    public void create(@RequestBody @Valid UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_MODERATOR")
    @SecurityRequirement(name = "oauth2_auth_code")
    public UserResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/hello")
    @Secured("ROLE_MODERATOR")
    @SecurityRequirement(name = "oauth2_auth_code")
    public String hello() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
package com.itm.space.backendresources.api.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UserRequest {

    @NotBlank(message = "Username should not be blank")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters long")
    private final String username;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid", regexp = ".+@.+\\..+")
    private final String email;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 4, message = "Password should be greater than 4 characters long")
    private final String password;

    @NotBlank(message = "First name should not be blank")
    private final String firstName;

    @NotBlank(message = "Last name should not be blank")
    private final String lastName;
}
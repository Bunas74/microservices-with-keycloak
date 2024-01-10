package com.itm.space.backendresources.exception;

import lombok.*;
import org.springframework.http.*;

@Getter
public class BackendResourcesException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BackendResourcesException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
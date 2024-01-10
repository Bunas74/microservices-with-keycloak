package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BackendResourcesException.class)
    public ResponseEntity<String> handleException(BackendResourcesException backendResourcesException) {
        return new ResponseEntity<>(backendResourcesException.getMessage(), backendResourcesException.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

}
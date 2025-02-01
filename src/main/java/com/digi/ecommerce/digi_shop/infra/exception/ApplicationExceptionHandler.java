package com.digi.ecommerce.digi_shop.infra.exception;

import com.digi.ecommerce.digi_shop.api.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleUserAlreadyExistsException(
            HttpServletRequest request,
            UserAlreadyExistsException ex) {
        log.error("handleUserAlreadyExistsException {} \n", request.getRequestURI(), ex);

        return ResponseEntity.status(CONFLICT)
                .body(new ApiErrorResponse<>("User already exists", List.of(ex.getMessage())));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleAuthenticationException(AuthenticationFailedException ex) {
        log.error("Authentication failed: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ApiErrorResponse<>("Authentication failed", List.of(ex.getMessage())));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Log exception
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

}

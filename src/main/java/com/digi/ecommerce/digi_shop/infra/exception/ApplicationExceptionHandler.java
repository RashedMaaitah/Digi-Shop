package com.digi.ecommerce.digi_shop.infra.exception;

import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUserAlreadyExistsException(
            HttpServletRequest request,
            UserAlreadyExistsException ex) {
        log.error("User already exists exception {} {} \n", request.getRequestURI(), ex);

        return ResponseEntity.status(CONFLICT)
                .body(
                        ApiResponse.error("User already exists",
                                List.of(ex.getMessage()),
                                CONFLICT.value(), request.getRequestURI())
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(
            HttpServletRequest request,
            RuntimeException ex) {
        log.error("Resource not found {} \n", request.getRequestURI(), ex);

        return ResponseEntity.status(NOT_FOUND)
                .body(
                        ApiResponse.error("Entity not found",
                                List.of(ex.getMessage()),
                                NOT_FOUND.value(), request.getRequestURI()));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(
            HttpServletRequest request,
            AuthenticationFailedException ex) {
        log.error("Authentication failed: {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ApiResponse.error("Authentication failed",
                                List.of(ex.getMessage()),
                                UNAUTHORIZED.value(), request.getRequestURI()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleRefreshTokenException(
            HttpServletRequest request,
            RefreshTokenException ex) {
        log.error("Refresh Token exception: {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ApiResponse.error("Invalid Refresh Token",
                                List.of(ex.getMessage()),
                                UNAUTHORIZED.value(), request.getRequestURI()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(
            HttpServletRequest request,
            AccessDeniedException ex) {
        log.error("Access denied error: {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(FORBIDDEN)
                .body(
                        ApiResponse.error("Access denied.",
                                List.of(ex.getMessage()),
                                FORBIDDEN.value(), request.getRequestURI()));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handleIncorrectPasswordException(
            HttpServletRequest request,
            IncorrectPasswordException ex
    ) {
        log.error("Incorrect password error: {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(FORBIDDEN)
                .body(
                        ApiResponse.error("Incorrect password",
                                List.of(ex.getMessage()),
                                FORBIDDEN.value(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(
            HttpServletRequest request,
            Exception ex) {
        log.error("Unexpected error: {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.error("An unexpected error occurred",
                                List.of(ex.getMessage()),
                                INTERNAL_SERVER_ERROR.value(), request.getRequestURI()));
    }

}

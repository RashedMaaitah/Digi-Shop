package com.digi.ecommerce.digi_shop.infra.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}

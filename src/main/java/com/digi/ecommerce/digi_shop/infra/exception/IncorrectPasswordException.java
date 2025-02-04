package com.digi.ecommerce.digi_shop.infra.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String s) {
        super(s);
    }
}

package com.digi.ecommerce.digi_shop.infra.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DecimalFormatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveDecimal {
    int maxIntegerDigits() default 8; // Maximum digits before the decimal point

    int maxFractionDigits() default 2; // Maximum digits after the decimal point

    String message() default "Invalid Positive Decimal Format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

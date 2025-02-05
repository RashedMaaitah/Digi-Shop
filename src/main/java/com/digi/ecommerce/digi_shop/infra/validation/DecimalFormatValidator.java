package com.digi.ecommerce.digi_shop.infra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalFormatValidator implements ConstraintValidator<PositiveDecimal, String> {
    private int maxIntegerDigits;
    private int maxFractionDigits;

    @Override
    public void initialize(PositiveDecimal constraintAnnotation) {
        this.maxIntegerDigits = constraintAnnotation.maxIntegerDigits();
        this.maxFractionDigits = constraintAnnotation.maxFractionDigits();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        String regex = String.format("^\\d{1,%d}(\\.\\d{1,%d})?$", maxIntegerDigits, maxFractionDigits);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            return false;
        }

        BigDecimal bigDecimal = new BigDecimal(value);
        return !bigDecimal.stripTrailingZeros().equals(BigDecimal.ZERO);
    }
}

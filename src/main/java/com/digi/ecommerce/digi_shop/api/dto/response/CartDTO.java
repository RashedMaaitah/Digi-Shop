package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

/**
 * DTO for Cart using records.
 *
 * @author Rashed Al Maaitah
 * @version 1.0
 */
public record CartDTO(@JsonProperty Long id,
                      @JsonProperty Long userId,
                      @JsonProperty List<CartItemDTO> cartItems,
                      @JsonProperty BigDecimal totalPrice,
                      @JsonProperty Instant createdAt,
                      @JsonProperty Instant updatedAt) {
    public CartDTO {
        totalPrice = calculateTotalPrice(cartItems);
    }

    private BigDecimal calculateTotalPrice(List<CartItemDTO> cartItems) {
        BigDecimal total = cartItems.stream()
                .map(item -> item.price().multiply(new BigDecimal(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
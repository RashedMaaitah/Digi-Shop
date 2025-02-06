package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * DTO for CartItem using records.
 *
 * @author Rashed Al Maaitah
 * @version 1.0
 */
public record CartItemDTO(@JsonProperty Long id,
                          @JsonProperty Long productId,
                          @JsonProperty String productName,
                          @JsonProperty Integer quantity,
                          @JsonProperty("product_price") BigDecimal price) {
}

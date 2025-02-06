package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderItemDTO(
        @JsonProperty Long id,
        @JsonProperty Long productId,
        @JsonProperty Integer quantity,
        @JsonProperty BigDecimal price
) {
}
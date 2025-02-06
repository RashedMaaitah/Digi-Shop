package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDTO(
        @JsonProperty Long id,
        @JsonProperty Long userId,
        @JsonProperty List<OrderItemDTO> orderItems,
        @JsonProperty BigDecimal totalAmount,
        @JsonProperty String status,
        @JsonProperty Instant createdAt
) {
}
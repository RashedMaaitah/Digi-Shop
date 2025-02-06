package com.digi.ecommerce.digi_shop.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrderStatusUpdateDTO(
        @NotBlank(message = "status must have a meaningful value")
        String status) {
}

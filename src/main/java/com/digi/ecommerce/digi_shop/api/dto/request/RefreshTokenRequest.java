package com.digi.ecommerce.digi_shop.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@JsonProperty("refresh-token") @NotBlank String refreshToken) {
}

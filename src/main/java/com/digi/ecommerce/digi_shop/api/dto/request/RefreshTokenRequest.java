package com.digi.ecommerce.digi_shop.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenRequest(@JsonProperty("refresh-token") String refreshToken) {
}

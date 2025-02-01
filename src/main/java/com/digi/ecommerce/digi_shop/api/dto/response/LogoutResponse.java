package com.digi.ecommerce.digi_shop.api.dto.response;

import java.time.Instant;

public record LogoutResponse(boolean success, String message, Instant timestamp) {
}

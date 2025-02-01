package com.digi.ecommerce.digi_shop.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthRequest(
        @Email @NotBlank String username,
        @NotBlank String password) {
}

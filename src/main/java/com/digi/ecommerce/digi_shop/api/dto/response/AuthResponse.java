package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record AuthResponse(
        @JsonProperty String id,
        @JsonProperty String firstName,
        @JsonProperty String lastName,
        @JsonProperty String username,
        @JsonProperty String[] roles,
        @JsonProperty String refreshToken
) {
}

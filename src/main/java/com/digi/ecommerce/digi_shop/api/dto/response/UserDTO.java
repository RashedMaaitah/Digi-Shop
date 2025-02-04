package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record UserDTO(
        @JsonProperty String id,
        @JsonProperty String firstName,
        @JsonProperty String lastName,
        @JsonProperty String email,
        @JsonProperty String[] roles,
        @JsonProperty Instant createdAt,
        @JsonProperty Instant updatedAt
) {
}

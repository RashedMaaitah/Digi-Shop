package com.digi.ecommerce.digi_shop.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(@JsonProperty @NotBlank String firstName,
                                @JsonProperty @NotBlank String lastName,
                                @JsonProperty @NotBlank @Email String username,
                                @JsonProperty
                                @NotBlank
                                @Size(min = 8, max = 25, message = "Password minimum length is 8")
                                String password) {
}

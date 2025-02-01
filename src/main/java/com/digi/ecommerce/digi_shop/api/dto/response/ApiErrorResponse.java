package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Represents an API error response.
 * This record encapsulates an error message that is typically included in a JSON response body.
 *
 * @param message represents the message field in the JSON response body.
 *
 * @author Rashed Al Maaitah
 * @version 1.0
 */
@JsonRootName("ApiError")
public record ApiErrorResponse<T>(
        @JsonProperty("message") String message,
        @JsonProperty("details") List<T> details) {
}

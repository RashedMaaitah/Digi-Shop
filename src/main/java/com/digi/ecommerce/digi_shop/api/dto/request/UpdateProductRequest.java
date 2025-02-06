package com.digi.ecommerce.digi_shop.api.dto.request;

import com.digi.ecommerce.digi_shop.infra.validation.PositiveDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(
        @JsonProperty String description,

        @JsonProperty
        @PositiveDecimal(message = "price must be a positive decimal decimal(8,2)")
        String price,

        @JsonProperty
        @Min(value = 1, message = "stock_quantity must be at least 1")
        @Max(value = 1200, message = "stock_quantity can't exceed 1200")
        Integer stock_quantity,

        @JsonProperty
        @Min(value = 0, message = "category_id must be at least 0")
        Long category_id) {
}

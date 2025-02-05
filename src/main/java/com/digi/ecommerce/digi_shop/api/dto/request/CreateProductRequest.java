package com.digi.ecommerce.digi_shop.api.dto.request;

import com.digi.ecommerce.digi_shop.infra.validation.PositiveDecimal;
import jakarta.validation.constraints.*;

public record CreateProductRequest(
        @NotBlank(message = "name can't be empty")
        String name,

        String description,

        @NotBlank(message = "price can't be empty")
        @PositiveDecimal(message = "price must be a positive decimal decimal(8,2)")
        String price,

        @NotNull(message = "stock_quantity can't be null")
        @Min(value = 1, message = "stock_quantity must be at least 1")
        @Max(value = 1200, message = "stock_quantity can't exceed 1200")
        Integer stock_quantity,

        @NotNull(message = "category_id can't be null")
        @Min(value = 0, message = "category_id must be at least 0")
        Long category_id) {
}

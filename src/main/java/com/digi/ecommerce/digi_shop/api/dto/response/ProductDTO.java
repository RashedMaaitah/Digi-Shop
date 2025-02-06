package com.digi.ecommerce.digi_shop.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDTO(@JsonProperty Long id,
                         @JsonProperty String name,
                         @JsonProperty String description,
                         @JsonProperty String price,
                         @JsonProperty("stock_quantity") String stockQuantity,
                         @JsonProperty("category") String categoryName) {
}

package com.digi.ecommerce.digi_shop.api.dto.request;

import com.digi.ecommerce.digi_shop.infra.validation.PositiveDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteria {
    @JsonProperty
    @PositiveDecimal
    private String lowestPrice;
    @JsonProperty
    @PositiveDecimal
    private String heightsPrice;
    @JsonProperty
    private String name;
    @JsonProperty
    private String category_name;

}

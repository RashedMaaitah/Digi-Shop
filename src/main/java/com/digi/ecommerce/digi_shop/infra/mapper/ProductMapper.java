package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockQuantity", source = "stock_quantity")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "price")
    public abstract Product toProduct(CreateProductRequest request);

    protected BigDecimal map(String value) {
        return new BigDecimal(value);
    }
}

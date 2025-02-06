package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.ProductDTO;
import com.digi.ecommerce.digi_shop.api.dto.response.UserDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockQuantity", source = "stock_quantity")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "price")
    public abstract Product toProduct(CreateProductRequest request);

    @Mapping(target = "categoryName", expression = "java( product.getCategory().getName() )")
    public abstract ProductDTO toProductDTO(Product product);

    public abstract List<ProductDTO> productDTOList(List<Product> products);

    protected BigDecimal map(String value) {
        return new BigDecimal(value);
    }

    protected String map(BigDecimal value) {
        return value.toString();
    }
}

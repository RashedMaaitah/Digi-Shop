package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.api.dto.request.UpdateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.ProductDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest productRequest);

    List<ProductDTO> getAllProducts();

    Page<Product> getProducts(PageDTO pageDTO,
                              ProductSearchCriteria productSearchCriteria);

    void updateProduct(Long id, UpdateProductRequest updateProductRequest);

    void deleteProduct(@Min(1) @Max(Long.MAX_VALUE) Long id);
}

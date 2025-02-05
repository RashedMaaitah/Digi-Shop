package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.repository.entity.Product;

public interface ProductService {
    Product createProduct(CreateProductRequest productRequest);
}

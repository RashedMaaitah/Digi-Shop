package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.infra.exception.EntityAlreadyExistsException;
import com.digi.ecommerce.digi_shop.infra.exception.EntityNotFoundException;
import com.digi.ecommerce.digi_shop.infra.mapper.ProductMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Category;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.CategoryRepository;
import com.digi.ecommerce.digi_shop.repository.repos.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product createProduct(CreateProductRequest productRequest) {
        Optional<Product> existentProduct = productRepository.findByName(productRequest.name());
        if (existentProduct.isPresent()) {
            throw new EntityAlreadyExistsException("name", productRequest.name(), Product.class);
        }
        // Make sure it belongs to an existent category
        Category category = categoryRepository
                .findById(productRequest.category_id())
                .orElseThrow(() -> new EntityNotFoundException(productRequest.category_id(), Category.class));

        Product productToSave = productMapper.toProduct(productRequest);
        productToSave.setCategory(category);

        return productRepository.save(productToSave);
    }

    private Product unwrapProduct(Optional<Product> optionalProduct, Long id) {
        if (optionalProduct.isPresent())
            return optionalProduct.get();
        else
            throw new EntityNotFoundException(id, Product.class);
    }
}

package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.api.dto.request.UpdateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.ProductDTO;
import com.digi.ecommerce.digi_shop.infra.exception.EntityAlreadyExistsException;
import com.digi.ecommerce.digi_shop.infra.exception.EntityNotFoundException;
import com.digi.ecommerce.digi_shop.infra.mapper.ProductMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Category;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.category.CategoryRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductCriteriaRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCriteriaRepository productCriteriaRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
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

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productMapper.productDTOList(productList);
    }

    @Override
    public Page<Product> getProducts(PageDTO pageDTO, ProductSearchCriteria productSearchCriteria) {
        return productCriteriaRepository.findAllWithFilters(pageDTO, productSearchCriteria);
    }

    @Transactional
    @Override
    public void updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));

        if (Objects.nonNull(updateProductRequest.category_id())) {
            Category category = categoryRepository.findById(updateProductRequest.category_id())
                    .orElseThrow(() -> new EntityNotFoundException(updateProductRequest.category_id(), Category.class));
            existingProduct.setCategory(category);
        }
        prepareProductToSave(existingProduct, updateProductRequest);
        existingProduct.setUpdatedAt(Instant.now());
        productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));

        productRepository.deleteById(existingProduct.getId());
    }

    private void prepareProductToSave(Product productToSave,
                                      UpdateProductRequest updateProductRequest) {
        if (Objects.nonNull(updateProductRequest.description())) {
            productToSave.setDescription(updateProductRequest.description());
        }
        if (Objects.nonNull(updateProductRequest.price())) {
            productToSave.setPrice(new BigDecimal(updateProductRequest.price()));
        }
        if (Objects.nonNull(updateProductRequest.stock_quantity())) {
            productToSave.setStockQuantity(updateProductRequest.stock_quantity());
        }
    }

}

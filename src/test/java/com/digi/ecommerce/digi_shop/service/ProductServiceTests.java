package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.infra.mapper.ProductMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Category;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.category.CategoryRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductCriteriaRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductCriteriaRepository productCriteriaRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void AddProduct_ShouldSaveProductSuccessfully() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(BigDecimal.valueOf(1200.00));
        product.setStockQuantity(10);
        product.setCategory(category);

        CreateProductRequest createProductRequest = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                "1200.00",
                10,
                1L
        );
        // Mock behavior
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        System.out.println("Mocked category: " + categoryRepository.findById(1L));

        // Act
        Product savedProduct = productService.createProduct(createProductRequest);

        // Assert
        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getId());
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(category, savedProduct.getCategory());

        verify(categoryRepository, times(1)).findById(1L);
    }
}

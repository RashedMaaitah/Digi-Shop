package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.infra.mapper.ProductMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Category;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.category.CategoryRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductCriteriaRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    // The service to test
    @InjectMocks
    private ProductServiceImpl productService;

    // Service Dependencies
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCriteriaRepository productCriteriaRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductMapper productMapper;

    // used objects
    private Category pcCategory;
    private Category kitchenCategory;
    List<Product> productList;


    @BeforeEach
    void setup() {
//        MockitoAnnotations.openMocks(this);
        pcCategory = new Category();
        kitchenCategory = new Category();
        pcCategory.setName("pc");
        kitchenCategory.setName("kitchen");
        pcCategory.setId(1L);
        kitchenCategory.setId(2L);
        initProductList();
    }


    @Test
    public void should_successfully_create_product() {
        CreateProductRequest productRequest = new CreateProductRequest(
                "product", "description", "100", 11, 1L
        );

        Product product = new Product();
        product.setId(1L);
        product.setName("product");
        product.setDescription("description");
        product.setPrice(new BigDecimal("100"));
        product.setStockQuantity(11);

        when(productRepository.findByName(productRequest.name()))
                .thenReturn(Optional.empty());
        when(categoryRepository.findById(productRequest.category_id()))
                .thenReturn(Optional.of(pcCategory));
        when(productMapper.toProduct(productRequest))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(product);

        Product responseProduct = productService.createProduct(productRequest);

        assertEquals(responseProduct, product);

        verify(productRepository, times(1)).findByName(any());
        verify(productRepository, times(1)).save(any());
        verify(categoryRepository, times(1)).findById(any());
        verify(productMapper, times(1)).toProduct(any());

    }

    @Test
    public void should_return_products_with_page_and_search_criteria() {
        PageDTO pageDTO = new PageDTO();
        ProductSearchCriteria searchCriteria = new ProductSearchCriteria();

        when(productCriteriaRepository.findAllWithFilters(any(), any()))
                .thenReturn(new PageImpl<>(productList));
        Page<Product> productPage = productService.getProducts(pageDTO, searchCriteria);

        assertEquals(productPage.get().count(), productList.size());
    }

    private void initProductList() {
        productList = List.of(
                new Product(1L, "spoon", "a tool to eat with",
                        BigDecimal.valueOf(10), 20, kitchenCategory,
                        Instant.now(), Instant.now()),
                new Product(2L, "GTX 9000", "a powerful PC GPU to make your gaming exp seamless",
                        BigDecimal.valueOf(9999), 5, pcCategory,
                        Instant.now(), Instant.now()),
                new Product(3L, "Stove", "an elegant looking stove to cook your food",
                        BigDecimal.valueOf(500), 14, kitchenCategory,
                        Instant.now(), Instant.now()),
                new Product(4L, "Red Dragon Mouse", "a high dp gaming mouse",
                        BigDecimal.valueOf(30), 20, pcCategory,
                        Instant.now(), Instant.now()),
                new Product(5L, "Silver Plates", "a set of authentic silver plates",
                        BigDecimal.valueOf(400), 2, kitchenCategory,
                        Instant.now(), Instant.now()),
                new Product(6L, "244 hz Gaming monitor", "a powerful gaming monitor by samsung",
                        BigDecimal.valueOf(1299), 6, pcCategory,
                        Instant.now(), Instant.now())
        );

    }
}













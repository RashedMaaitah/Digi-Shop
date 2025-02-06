package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.api.dto.request.UpdateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.ProductDTO;
import com.digi.ecommerce.digi_shop.infra.mapper.ProductMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.digi.ecommerce.digi_shop.common.PathConstants.PRODUCTS_BASE;
import static com.digi.ecommerce.digi_shop.common.PathConstants.PRODUCTS_ID;

@RestController
@RequestMapping(PRODUCTS_BASE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final HttpServletRequest httpServletRequest;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            PageDTO pageDTO,
            ProductSearchCriteria productSearchCriteria) {
        Page<ProductDTO> productDTOPage =
                productService.getProducts(pageDTO, productSearchCriteria)
                        .map(productMapper::toProductDTO);
        return ResponseEntity.ok(
                ApiResponse.success(List.of(productDTOPage),
                        "Fetched all products",
                        httpServletRequest.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @RequestBody @Valid CreateProductRequest createProductRequest) {

        Product savedProduct = productService.createProduct(createProductRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(List.of(savedProduct),
                        "Product Created Successfully",
                        httpServletRequest.getRequestURI()));
    }

    @PatchMapping(PRODUCTS_ID)
    public ResponseEntity<ApiResponse<String>> updateProduct(
            @PathVariable("id")
            @Min(1) @Max(Long.MAX_VALUE)
            Long id,
            @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok(
                ApiResponse.success(List.of(),
                        "Product Updated Successfully",
                        httpServletRequest.getRequestURI()));
    }

    @DeleteMapping(PRODUCTS_ID)
    public ResponseEntity<Void> deleteProduct(
            @PathVariable
            @Min(1) @Max(Long.MAX_VALUE)
            Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

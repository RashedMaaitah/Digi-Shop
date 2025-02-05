package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.UpdateProductRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.ProductDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        // TODO Fetch all Products and Map them to Product DTO
//        productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.success(List.of(),
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
            @PathVariable
            @Min(1) @Max(Long.MAX_VALUE)
            Long id,
            @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        // TODO make sure the id is for an existing product if so update the sent fields only
        return ResponseEntity.ok(
                ApiResponse.success(List.of(),
                        "Product Updated Successfully",
                        httpServletRequest.getRequestURI()));
    }

    @DeleteMapping(PRODUCTS_ID)
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @PathVariable
            @Min(1) @Max(Long.MAX_VALUE)
            Long id) {

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(List.of(),
                        "Product Updated Successfully",
                        httpServletRequest.getRequestURI()));
    }
}

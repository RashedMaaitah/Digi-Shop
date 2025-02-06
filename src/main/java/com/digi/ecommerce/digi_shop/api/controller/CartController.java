package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.CartDTO;
import com.digi.ecommerce.digi_shop.infra.security.dto.UserDetailsDTO;
import com.digi.ecommerce.digi_shop.service.CartService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.digi.ecommerce.digi_shop.common.PathConstants.CART_BASE;
import static com.digi.ecommerce.digi_shop.common.PathConstants.CART_ITEM_ID;

@RequiredArgsConstructor
@RestController
@RequestMapping(CART_BASE)
public class CartController {

    private final CartService cartService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping
    public ResponseEntity<ApiResponse<CartDTO>> getCartForCurrentUser(
            @AuthenticationPrincipal UserDetailsDTO userDetails
    ) {
        CartDTO cartDTO = cartService.getCartForCurrentUser(userDetails.getId());
        return ResponseEntity.ok(
                ApiResponse.success(List.of(cartDTO),
                        "Fetched Cart Successfully",
                        httpServletRequest.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addItemToCart(
            @AuthenticationPrincipal UserDetailsDTO userDetails,
            @RequestParam @Min(value = 1, message = "Product id starts from 1") Long productId,
            @RequestParam @Min(value = 1, message = "Minimum quantity is 1") int quantity) {

        cartService.addItemToCart(userDetails.getId(), productId, quantity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(List.of(),
                        "Added Item Successfully",
                        httpServletRequest.getRequestURI()));
    }


    @PatchMapping(CART_ITEM_ID)
    public ResponseEntity<ApiResponse<String>> updateCartItemQuantity(
            @PathVariable
            @Min(value = 1, message = "CartItem id starts from 1")
            Long cartItemId,
            @RequestParam
            @Min(value = 1, message = "Minimum quantity is 1")
            int quantity) {

        cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(List.of(),
                        "Updated Cart Item Successfully",
                        httpServletRequest.getRequestURI()));
    }

    @DeleteMapping(CART_ITEM_ID)
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable
            @Min(value = 1, message = "CartItem id starts from 1")
            Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

}

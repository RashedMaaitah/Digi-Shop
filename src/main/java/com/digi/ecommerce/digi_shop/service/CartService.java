package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.CartDTO;

public interface CartService {
    CartDTO getCartForCurrentUser(Long userId);

    void addItemToCart(Long userId, Long productId, int quantity);

    void updateCartItemQuantity(Long cartItemId, int quantity);

    void removeItemFromCart(Long cartItemId);

}

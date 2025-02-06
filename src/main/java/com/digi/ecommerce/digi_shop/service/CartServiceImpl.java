package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.CartDTO;
import com.digi.ecommerce.digi_shop.infra.exception.EntityNotFoundException;
import com.digi.ecommerce.digi_shop.infra.mapper.CartMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Cart;
import com.digi.ecommerce.digi_shop.repository.entity.CartItem;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.cart.CartItemRepository;
import com.digi.ecommerce.digi_shop.repository.repos.cart.CartRepository;
import com.digi.ecommerce.digi_shop.repository.repos.product.ProductRepository;
import com.digi.ecommerce.digi_shop.repository.repos.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addItemToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, User.class));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCreatedAt(Instant.now());
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, Product.class));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    existingItem.setAddedAt(Instant.now());
                    return existingItem;
                })
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(quantity);
                    newItem.setAddedAt(Instant.now());
                    return newItem;
                });

        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(cartItemId, CartItem.class));

        cartItem.setQuantity(quantity);
        cartItem.setAddedAt(Instant.now());
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(cartItemId, CartItem.class));

        cartItemRepository.delete(cartItem);
    }

    public CartDTO getCartForCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, User.class));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user" + userId));

        return CartMapper.INSTANCE.toCartDTO(cart);
    }

}

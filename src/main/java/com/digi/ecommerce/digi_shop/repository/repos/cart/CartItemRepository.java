package com.digi.ecommerce.digi_shop.repository.repos.cart;

import com.digi.ecommerce.digi_shop.repository.entity.Cart;
import com.digi.ecommerce.digi_shop.repository.entity.CartItem;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}

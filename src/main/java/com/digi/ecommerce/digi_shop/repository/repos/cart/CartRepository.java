package com.digi.ecommerce.digi_shop.repository.repos.cart;

import com.digi.ecommerce.digi_shop.repository.entity.Cart;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}

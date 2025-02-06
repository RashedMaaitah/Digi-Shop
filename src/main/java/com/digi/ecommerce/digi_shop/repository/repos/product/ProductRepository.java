package com.digi.ecommerce.digi_shop.repository.repos.product;

import com.digi.ecommerce.digi_shop.repository.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(@Size(max = 100) @NotNull String name);
}

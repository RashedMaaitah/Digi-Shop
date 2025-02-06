package com.digi.ecommerce.digi_shop.repository.repos.category;

import com.digi.ecommerce.digi_shop.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

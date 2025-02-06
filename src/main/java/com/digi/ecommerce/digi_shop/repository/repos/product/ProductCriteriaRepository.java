package com.digi.ecommerce.digi_shop.repository.repos.product;

import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.CriteriaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCriteriaRepository extends CriteriaRepository<Product, ProductSearchCriteria> {
    public ProductCriteriaRepository(EntityManager entityManager,
                                     ProductCriteriaFilter criteriaFilter) {
        super(entityManager, criteriaFilter, Product.class);
    }
}

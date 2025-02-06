package com.digi.ecommerce.digi_shop.repository.repos.product;

import com.digi.ecommerce.digi_shop.api.dto.request.ProductSearchCriteria;
import com.digi.ecommerce.digi_shop.repository.entity.Category;
import com.digi.ecommerce.digi_shop.repository.entity.Product;
import com.digi.ecommerce.digi_shop.repository.repos.CriteriaFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCriteriaFilter implements CriteriaFilter<ProductSearchCriteria> {
    @Override
    public Predicate getPredicate(ProductSearchCriteria searchCriteria, Root<?> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        /*
        TODO Filter by
          1- low <= price <= high
          2- Product Name
          3- By Category
         */
        BigDecimal low = BigDecimal.ZERO;
        BigDecimal high = BigDecimal.valueOf(Long.MAX_VALUE);

        if (Objects.nonNull(searchCriteria.getLowestPrice())) {
            low = new BigDecimal(searchCriteria.getLowestPrice());
        }
        if (Objects.nonNull(searchCriteria.getHeightsPrice())) {
            high = new BigDecimal(searchCriteria.getHeightsPrice());
        }

        if (low.compareTo(BigDecimal.ZERO) > 0 || high.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) < 0) {
            predicates.add(
                    criteriaBuilder.between(
                            root.get("price"),
                            low,
                            high
                    )
            );
        }
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(root.get("name"), "%" + searchCriteria.getName() + "%")
            );
        }
        if (Objects.nonNull(searchCriteria.getCategory_name())) {
            Join<Product, Category> categoryJoin = root.join("category");

            predicates.add(
                    criteriaBuilder.like(categoryJoin.get("name"), "%" + searchCriteria.getCategory_name() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

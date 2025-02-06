package com.digi.ecommerce.digi_shop.repository.repos.user;

import com.digi.ecommerce.digi_shop.api.dto.request.UserSearchCriteria;
import com.digi.ecommerce.digi_shop.repository.repos.CriteriaFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserCriteriaFilter implements CriteriaFilter<UserSearchCriteria> {

    @Override
    public Predicate getPredicate(UserSearchCriteria searchCriteria, Root<?> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(searchCriteria.getFirstName())) {
            predicates.add(
                    criteriaBuilder.like(root.get("firstName"), "%" + searchCriteria.getFirstName() + "%")
            );
        }
        if (Objects.nonNull(searchCriteria.getLastName())) {
            predicates.add(
                    criteriaBuilder.like(root.get("lastName"), "%" + searchCriteria.getLastName() + "%")
            );
        }
        if (Objects.nonNull(searchCriteria.getEmail())) {
            predicates.add(
                    criteriaBuilder.like(root.get("email"), "%" + searchCriteria.getEmail() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

package com.digi.ecommerce.digi_shop.repository.repos;

import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;

public abstract class CriteriaRepository<T, S> {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaFilter<S> criteriaFilter;
    private final Class<T> entityClass;


    public CriteriaRepository(EntityManager entityManager,
                              CriteriaFilter<S> criteriaFilter,
                              Class<T> entityClass) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaFilter = criteriaFilter;
        this.entityClass = entityClass;
    }

    public Page<T> findAllWithFilters(PageDTO pageDTO, S searchCriteria) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        Predicate predicate = criteriaFilter.getPredicate(searchCriteria, root, criteriaBuilder);
        criteriaQuery.where(predicate);

        setOrder(pageDTO, criteriaQuery, root);

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageDTO.getPage() * pageDTO.getPageSize());
        typedQuery.setMaxResults(pageDTO.getPageSize());

        Pageable pageable = getPageable(pageDTO);
        Long entityCount = getEntityCount(searchCriteria);

        return new PageImpl<>(typedQuery.getResultList(), pageable, entityCount);
    }

    private void setOrder(PageDTO userPage, CriteriaQuery<T> criteriaQuery, Root<T> root) {
        if (userPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(userPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(userPage.getSortBy())));
        }
    }

    private Pageable getPageable(PageDTO userPage) {
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        return PageRequest.of(userPage.getPage(), userPage.getPageSize(), sort);
    }

    private Long getEntityCount(S searchCriteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(countRoot));

        Predicate countPredicate = criteriaFilter.getPredicate(searchCriteria, countRoot, criteriaBuilder);
        if (countPredicate != null) {
            countQuery.where(countPredicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }

}

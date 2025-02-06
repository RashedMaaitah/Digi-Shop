package com.digi.ecommerce.digi_shop.repository.repos.user;

import com.digi.ecommerce.digi_shop.api.dto.request.UserSearchCriteria;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.CriteriaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserCriteriaRepository extends CriteriaRepository<User, UserSearchCriteria> {
    public UserCriteriaRepository(EntityManager entityManager,
                                  UserCriteriaFilter userCriteriaFilter) {
        super(entityManager, userCriteriaFilter, User.class);
    }
}

package com.digi.ecommerce.digi_shop.repository.repos.user;

import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    @Modifying
    @Query("""
            UPDATE User user
            SET user.refreshToken = null,
                user.refreshTokenExpiry = null
            WHERE user.email = :email
            """)
    void invalidateRefreshTokenByEmail(String email);

}

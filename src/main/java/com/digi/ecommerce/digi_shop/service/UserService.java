package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.AuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import jakarta.validation.Valid;

public interface UserService {
    User updateRefreshToken(String email, String refreshToken);

    User validateRefreshToken(String refreshToken);

    void invalidateRefreshTokenByEmail(String email);

    User getUserWithEmail(String email);

    AuthResponse createUser(@Valid CreateUserRequest request);
}

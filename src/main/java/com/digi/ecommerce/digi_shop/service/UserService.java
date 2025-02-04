package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.ChangePasswordRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.UserAuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    User updateRefreshToken(String email, String refreshToken);

    User validateRefreshToken(String refreshToken);

    void invalidateRefreshTokenByEmail(String email);

    UserAuthResponse createUser(CreateUserRequest request);

    List<User> getAllUsers();

    void updatePassword(Long id, ChangePasswordRequest changePasswordRequest);
}

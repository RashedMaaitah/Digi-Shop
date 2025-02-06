package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.ChangePasswordRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.UserSearchCriteria;
import com.digi.ecommerce.digi_shop.api.dto.response.UserAuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User updateRefreshToken(String email, String refreshToken);

    User validateRefreshToken(String refreshToken);

    void invalidateRefreshTokenByEmail(String email);

    UserAuthResponse createUser(CreateUserRequest request);

    List<User> getAllUsers();

    Page<User> getUsers(PageDTO pageDTO,
                        UserSearchCriteria userSearchCriteria);

    void updatePassword(Long id, ChangePasswordRequest changePasswordRequest);
}

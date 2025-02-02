package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.AuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.common.Roles;
import com.digi.ecommerce.digi_shop.infra.exception.RefreshTokenException;
import com.digi.ecommerce.digi_shop.infra.exception.UserAlreadyExistsException;
import com.digi.ecommerce.digi_shop.infra.mapper.UserMapper;
import com.digi.ecommerce.digi_shop.infra.security.jwt.JwtService;
import com.digi.ecommerce.digi_shop.repository.entity.Role;
import com.digi.ecommerce.digi_shop.repository.entity.RoleId;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Value("${app.security.jwt.refresh-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public User updateRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(Instant.now().plusMillis(refreshTokenExpiration));

        return userRepository.save(user);
    }

    @Override
    public User validateRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException(refreshToken, "Invalid refresh token"));

        if (!jwtService.validateToken(refreshToken)) {
            user.setRefreshToken(null);
            user.setRefreshTokenExpiry(null);
            userRepository.save(user);
            throw new RefreshTokenException(refreshToken, "Refresh token has expired. Please login again.");
        }

        return user;
    }

    @Transactional
    @Override
    public void invalidateRefreshTokenByEmail(String email) {
        userRepository.invalidateRefreshTokenByEmail(email);
    }

    @Override
    public User getUserWithEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public AuthResponse createUser(CreateUserRequest request) {
        if (userRepository.findByEmailIgnoreCase(request.username()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = userRepository.save(userMapper.toUser(request));

        Role role = new Role();
        role.setUser(user);
        RoleId roleId = new RoleId();
        roleId.setRoleName(Roles.USER.name());
        roleId.setUserId(user.getId());
        role.setId(roleId);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}

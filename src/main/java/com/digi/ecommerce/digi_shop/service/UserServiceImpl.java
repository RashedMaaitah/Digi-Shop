package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.request.ChangePasswordRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.UserAuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.common.Roles;
import com.digi.ecommerce.digi_shop.infra.exception.EntityAlreadyExistsException;
import com.digi.ecommerce.digi_shop.infra.exception.IncorrectPasswordException;
import com.digi.ecommerce.digi_shop.infra.exception.RefreshTokenException;
import com.digi.ecommerce.digi_shop.infra.mapper.UserMapper;
import com.digi.ecommerce.digi_shop.infra.security.jwt.JwtService;
import com.digi.ecommerce.digi_shop.repository.entity.Role;
import com.digi.ecommerce.digi_shop.repository.entity.RoleId;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.UserRepository;
import com.digi.ecommerce.digi_shop.infra.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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
    public UserAuthResponse createUser(CreateUserRequest request) {
        if (userRepository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new EntityAlreadyExistsException("email", request.email(), User.class);
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

        return userMapper.toUserAuthResponse(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updatePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        User user = unwrapUser(userRepository.findById(id), id);

        if (!passwordEncoder.matches(changePasswordRequest.oldPassword(), user.getPasswordHash())) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.newPassword()));
        user.setUpdatedAt(Instant.now());
        user.setRefreshToken(null);
        user.setRefreshTokenExpiry(null);
        userRepository.save(user);
    }

    private User unwrapUser(Optional<User> optionalUser, Long id) {
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new EntityNotFoundException(id, User.class);
    }
}

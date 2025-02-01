package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.response.AuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.repository.entity.Role;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "username")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "passwordHash",
            expression = "java( passwordEncoder.encode(request.password()) )")
    public abstract User toUser(CreateUserRequest request);

    @Mapping(target="username", source = "email")
    public abstract AuthResponse toUserResponse(User user);

    protected String[] map(Set<Role> roles) {
        return roles
                .stream()
                .map(role -> role.getId().getRoleName())
                .toArray(String[]::new);
    }
}

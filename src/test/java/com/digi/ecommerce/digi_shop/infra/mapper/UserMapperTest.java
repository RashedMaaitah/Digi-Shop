package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.UserAuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.UserDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Role;
import com.digi.ecommerce.digi_shop.repository.entity.RoleId;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User user;
    private Role role;
    private CreateUserRequest createUserRequest;

    @BeforeEach
    void setUp() {
        initUserAndRole();
        initCreateUserRequest();
    }


    @Test
    public void shouldMapUserToUserDTO() {
        UserDTO userDTO = userMapper.toUserDTO(user);

        assertEquals(userDTO.firstName(), user.getFirstName());
        assertEquals(userDTO.lastName(), user.getLastName());
        assertEquals(userDTO.email(), user.getEmail());
        assertEquals(userDTO.createdAt(), user.getCreatedAt());
        assertEquals(userDTO.updatedAt(), user.getUpdatedAt());
        assertEquals(userDTO.roles()[0], role.getId().getRoleName());
    }

    @Test
    public void shouldMapCreateUserRequestToUser() {
        User user = userMapper.toUser(createUserRequest);

        assertEquals(createUserRequest.firstName(), user.getFirstName());
        assertEquals(createUserRequest.lastName(), user.getLastName());
        assertEquals(createUserRequest.email(), user.getEmail());
    }

    @Test
    public void shouldMapUserToUserAuthResponse() {
        UserAuthResponse userAuthResponse = userMapper.toUserAuthResponse(user);

        assertEquals(userAuthResponse.id(), user.getId().toString());
        assertEquals(userAuthResponse.email(), user.getEmail());
        assertEquals(userAuthResponse.firstName(), user.getFirstName());
        assertEquals(userAuthResponse.lastName(), user.getLastName());
        assertEquals(userAuthResponse.roles()[0], role.getId().getRoleName());
        assertEquals(userAuthResponse.refreshToken(), user.getRefreshToken());
    }

    private void initUserAndRole() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Rashed");
        user.setLastName("Al Maaitah");
        user.setEmail("fake@email.test");
        RoleId roleId = new RoleId();
        roleId.setUserId(1L);
        roleId.setRoleName("User");
        role = new Role();
        role.setId(roleId);
        user.setRoles(Set.of(role));
        user.setCreatedAt(Instant.ofEpochMilli(1000));
        user.setUpdatedAt(Instant.ofEpochMilli(1000));
        user.setRefreshToken("fakeRefreshToken");
        user.setRefreshTokenExpiry(Instant.ofEpochMilli(1000));
    }

    private void initCreateUserRequest() {
        createUserRequest = new CreateUserRequest("Rashed", "Al Maaitah", "fake@email.test", "password123");
    }
}











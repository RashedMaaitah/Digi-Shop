package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.request.PageDTO;
import com.digi.ecommerce.digi_shop.api.dto.request.UserSearchCriteria;
import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.UserDTO;
import com.digi.ecommerce.digi_shop.infra.mapper.UserMapper;
import com.digi.ecommerce.digi_shop.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.digi.ecommerce.digi_shop.common.PathConstants.USERS_BASE;

@Tag(name = "Users",
        description = "Public APIs for managing users")
@RestController
@RequestMapping(USERS_BASE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpServletRequest httpServletRequest;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUsers(
            PageDTO pageDTO,
            UserSearchCriteria userSearchCriteria) {

        Page<UserDTO> userDTOPage = userService.getUsers(pageDTO, userSearchCriteria)
                .map(userMapper::toUserDTO);

        return ResponseEntity.
                ok(ApiResponse.success(List.of(userDTOPage),
                        "Fetched all users successfully", httpServletRequest.getRequestURI()));
    }


}

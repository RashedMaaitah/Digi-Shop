package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.request.AuthRequest;
import com.digi.ecommerce.digi_shop.api.dto.request.RefreshTokenRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.AuthResponse;
import com.digi.ecommerce.digi_shop.api.dto.request.CreateUserRequest;
import com.digi.ecommerce.digi_shop.api.dto.response.LogoutResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.RefreshTokenResponse;
import com.digi.ecommerce.digi_shop.infra.exception.AuthenticationFailedException;
import com.digi.ecommerce.digi_shop.infra.security.dto.UserDetailsDTO;
import com.digi.ecommerce.digi_shop.infra.security.jwt.JwtService;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;

import static com.digi.ecommerce.digi_shop.common.AuthConstants.BEARER_PREFIX;

@Tag(name = "Authentication",
        description = "Public APIs for managing users authentication and registration")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(description = """
            Authenticate a user before accessing any protected resource
            returns a valid access token if it succeeded""")
    @PostMapping("signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );

            String accessToken = jwtService.createJwtAccessToken(authentication.getName());
            String refreshToken = jwtService.createJWTRefreshToken(authentication.getName());

            User user = userService.updateRefreshToken(authentication.getName(), refreshToken);

            var response = new AuthResponse(
                    user.getId().toString(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRoles().stream()
                            .map(role -> role.getId().getRoleName())
                            .toArray(String[]::new),
                    refreshToken
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
                    .body(response);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

    @Operation(
            summary = "Register a new user",
            description = """
                    This endpoint allows for the registration of a new user in the system.
                    The user will need to provide the required details such as first name, last name,
                    email, and password. Upon successful registration, the system will create a new user
                    and return the details of the created user, including a generated ID.
                    """
    )
    @PostMapping("signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid CreateUserRequest request) {
        AuthResponse authResponse = userService.createUser(request);

        return ResponseEntity.created(URI.create("/users/" + authResponse.id()))
                .body(authResponse);
    }

    @Operation(description = """
            An API call, to refresh user JWT token without signing again,
            to be able to continue be authenticated and use the system.
            """)
    @PostMapping("refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.refreshToken();

        User user = userService.validateRefreshToken(requestRefreshToken);

        String newAccessToken = jwtService.createJwtAccessToken(user.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + newAccessToken)
                .body(new RefreshTokenResponse(requestRefreshToken));
    }

    @Operation(description = """
            An API call, to logout the user,
            But has to re-authenticate again to access the system.
            """,
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("signout")
    public ResponseEntity<LogoutResponse> logoutUser() {
        return getUserFromSecurityContext()
                .map(user -> {
                    userService.invalidateRefreshTokenByEmail(user.getUsername());
                    SecurityContextHolder.getContext().setAuthentication(null);

                    LogoutResponse response = new LogoutResponse(true, "You have been successfully logged out.", Instant.now());
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated. Please sign in."));
    }

    private Optional<UserDetailsDTO> getUserFromSecurityContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal() instanceof UserDetailsDTO user ? Optional.of(user) : Optional.empty();
    }
}

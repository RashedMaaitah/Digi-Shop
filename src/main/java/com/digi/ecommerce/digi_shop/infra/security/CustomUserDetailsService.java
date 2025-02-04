package com.digi.ecommerce.digi_shop.infra.security;

import com.digi.ecommerce.digi_shop.infra.security.dto.UserDetailsDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Role;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service impl for loading user-specific data.
 * This class implements {@link UserDetailsService} to provide user auth details
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsDTO loadUserByUsername(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow((() -> new UsernameNotFoundException("Email Not found")));

        return new UserDetailsDTO(user.getId(), user.getEmail(), user.getPasswordHash(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Maps a set of roles to a collection of granted authorities.
     *
     * @param roles the set of roles assigned to the user.
     * @return a collection of {@link GrantedAuthority} representing the user's roles.
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId().getRoleName()))
                .collect(Collectors.toSet());
    }
}

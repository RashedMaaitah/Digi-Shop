package com.digi.ecommerce.digi_shop.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the User domain model as a JPA Entity
 *
 * @author Rashed Al Maaitah
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Basic(optional = false)
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Size(max = 100)
    @NotNull
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 50)
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Basic(optional = false)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @NotNull
    @Basic(optional = false)
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

}

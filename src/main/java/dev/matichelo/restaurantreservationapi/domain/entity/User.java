package dev.matichelo.restaurantreservationapi.domain.entity;

import dev.matichelo.restaurantreservationapi.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // La anotación @Enumerated se utiliza para mapear una columna de tipo enum en la base de datos.
    @Enumerated(EnumType.STRING)
    private Role role;
}

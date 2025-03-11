package dev.matichelo.restaurantreservationapi.security;

import dev.matichelo.restaurantreservationapi.domain.entity.User;
import dev.matichelo.restaurantreservationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Spring Security : spring security es un framework que permite la autenticación y autorización de usuarios en una aplicación web
    // UserDetailsService : Interfaz que permite cargar un usuario por su nombre de usuario y devolver un objeto UserDetails que Spring Security puede usar para autenticar al usuario
    // lo que digo de cargar un usuario por su nombre de usuario es que se puede buscar un usuario en la base de datos por su nombre de usuario y devolver un objeto UserDetails que Spring Security puede usar para autenticar al usuario

    // Jwt : JSON Web Token (JWT) es un estándar abierto basado en JSON propuesto por IETF para la creación de tokens
    // de acceso que permiten la propagación segura de la identidad del usuario y la autenticación de un servidor a otro.

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(username).orElseThrow( () -> new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}

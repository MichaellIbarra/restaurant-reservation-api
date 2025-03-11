package dev.matichelo.restaurantreservationapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
// extends SecurityConfigurerAdapter es una clase abstracta que implementa SecurityConfigurer y se encarga de configurar la seguridad de la aplicación
// <DefaultSecurityFilterChain, HttpSecurity> DefaultSecurityFilterChain es una clase que implementa FilterChainProxy y HttpSecurity es una clase que implementa SecurityBuilder
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;


    @Override
    public void configure(HttpSecurity http) throws Exception{
        // JWTFilter es una clase que extiende de GenericFilterBean y se encarga de filtrar las peticiones HTTP para validar el token JWT
        // new JWTFilter(tokenProvider) : Crea una instancia de JWTFilter con el proveedor de token proporcionado se
        // pone en la variable tokenProvider porque contiene la clave secreta HMAC y el analizador de token JWT
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);
        // addFilterBefore() : Método que agrega un filtro personalizado antes de otro filtro en la cadena de filtros
        //  de seguridad de Spring
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

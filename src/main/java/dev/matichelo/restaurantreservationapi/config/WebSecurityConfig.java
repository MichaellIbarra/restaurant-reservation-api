package dev.matichelo.restaurantreservationapi.config;

import dev.matichelo.restaurantreservationapi.security.JWTConfigurer;
import dev.matichelo.restaurantreservationapi.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// WebSecurityConfig es una clase de configuración de Spring que se encarga de configurar la seguridad de la aplicación
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //  Cors (Cross-Origin Resource Sharing) es una especificación que permite que los recursos de una
                //  página web se soliciten desde otro dominio fuera del dominio que sirvió la página actual.
                // Cors controla cómo se puede acceder a los recursos de un servidor desde un dominio diferente.
                // Customizer.withDefaults() es un método que devuelve un Customizer que permite configurar CORS que
                //  hace que el servidor permita solicitudes desde cualquier origen.
                .cors(Customizer.withDefaults())
                // csrf (Cross-Site Request Forgery) es un tipo de ataque malicioso en el que se ejecutan acciones
                //  no autorizadas en nombre del usuario que ha iniciado sesión en la aplicación.
                // CSRF no es un problema en las aplicaciones que utilizan tokens de seguridad para proteger las solicitudes.
                .csrf(AbstractHttpConfigurer::disable)
                // Configuración de la autorización de las solicitudes HTTP
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/auth/sign-up", "/auth/sign-in").permitAll()
                        .requestMatchers("/api/v1/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                // Configuración la gestión de sesiones para que no se cree una sesión por defecto
                // SessionCreationPolicy.STATELESS indica que no se creará una sesión por defecto STATELESS es un estado de sesión en el que el servidor no guarda ningún estado de sesión.
                .sessionManagement( h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configuración de la seguridad de la aplicación para que utilice el tokenProvider y JWTConfigurer
                .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());

        return httpSecurity.build();
    }

    // Se crea un bean de tipo PasswordEncoder para que Spring Security pueda utilizarlo para codificar y decodificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

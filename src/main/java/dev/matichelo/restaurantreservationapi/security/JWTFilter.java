package dev.matichelo.restaurantreservationapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
// JWTFilter es una clase que extiende de GenericFilterBean y se encarga de filtrar las peticiones HTTP para validar el token JWT
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Es para que el filtro se ejecute solo en las peticiones HTTP , HttpServletRequest es una interfaz que extiende de ServletRequest y proporciona métodos para obtener información sobre la solicitud HTTP
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // bearerToken : es el token de autorización que se envía en la cabecera de la solicitud HTTP
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        // StringUtils.hasText() : Método que devuelve true si la cadena no es nula y tiene una longitud mayor que 0
        // bearerToken.startsWith("Bearer ") : Método que devuelve true si el token de autorización comienza con "Bearer "
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            // token : es el token de autorización sin el prefijo "Bearer "
            String token = bearerToken.substring(7);
            // authentication : es la autenticación del usuario que se obtiene a partir del token
            Authentication authentication = tokenProvider.getAuthentication(token);
            // SecurityContextHolder : Es una clase que proporciona acceso al contexto de seguridad de Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // chain.doFilter() : Método que permite que la solicitud actual continúe hacia el siguiente filtro en la cadena
        chain.doFilter(request, response);

    }
}

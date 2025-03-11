package dev.matichelo.restaurantreservationapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity-in-milliseconds}")
    private Long jwtValidityInMs;

    // Key : Es una interfaz que representa una clave criptográfica secreta o pública
    private Key key;
    // JwtParser : Interfaz que permite analizar (parsear) un token JWT
    private JwtParser jwtParser;

    // @PostConstruct : Anotación que se utiliza en un método que debe ejecutarse después de que se haya completado la inyección de dependencias para realizar cualquier inicialización
    // Este método es inicializar la clave secreta HMAC y el analizador de token JWT
    @PostConstruct
    public void init(){
        // Keys : es una clase que proporciona métodos de fábrica para crear claves criptográficas
        // hmacShaKeyFor: Crea una clave secreta HMAC utilizando la codificación Base64 de la clave proporcionada
        // Decoders.BASE64.decode(jwtSecret) : Decodifica la clave jwtSecret de Base64, Decodificar es el proceso de convertir datos codificados o cifrados en un formato legible
        // key : es la clave secreta HMAC creada a partir de la clave jwtSecret
        // decode() : Método que decodifica una cadena codificada en Base64
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        // Jwts : es una clase que proporciona métodos de fábrica para crear y analizar tokens JWT
        // parserBuilder() : Crea un constructor de analizador de token JWT
        // setSigningKey(key): Establece la clave de firma que se utilizará para validar el token
        // jwtParser : Es el analizador de token JWT creado con la clave secreta HMAC
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    // createAccessToken() : Método que crea un token JWT para el usuario autenticado
    public String createAccessToken(Authentication authentication){
        // getAuthorities() : Devuelve una colección de objetos GrantedAuthority que representan los roles del usuario autenticado
        // stream() : Devuelve un Stream que consiste en los elementos de esta secuencia
        // findFirst() : Devuelve un Optional que describe el primer elemento de esta secuencia, o un Optional vacío si la secuencia está vacía
        // orElseThrow(RuntimeException::new) : Devuelve el valor si está presente, de lo contrario lanza una excepción proporcionada por la excepción de proveedor
        // getAuthority() : Devuelve el nombre del rol
        // role : es el nombre del rol del usuario autenticado
        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getAuthority();
        // builder() : Crea un constructor de token JWT
        // setSubject(authentication.getName()) : Establece el nombre de usuario del usuario autenticado como el sujeto del token
        // claim("role", role) : Establece el rol del usuario autenticado como una reclamación en el token
        // signWith(key, SignatureAlgorithm.HS512) : Firma el token con la clave secreta HMAC y el algoritmo de firma HS512
        // setExpiration(new Date(System.currentTimeMillis() + jwtValidityInMs * 1000)) : Establece la fecha de vencimiento del token
        // compact() : Compacta el token en una cadena
        // return : Devuelve el token JWT
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInMs * 1000))
                .compact();
    }

    // getAuthentication() : Método que analiza el token JWT y devuelve un objeto Authentication que contiene la información del usuario autenticado
    public Authentication getAuthentication(String token){
        // parseClaimsJws(token) : Analiza el token JWT y devuelve un objeto Claims que contiene las reclamaciones del token
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String role = claims.get("role").toString();
        System.out.println("role: " + role);
        // authorities : Es una lista de objetos GrantedAuthority que representan los roles del usuario autenticado
        // GrantendAuthority : Es una interfaz que representa una autoridad concedida a un usuario
        // SimpleGrantedAuthority : Es una implementación de la interfaz GrantedAuthority que representa una autoridad concedida a un usuario
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        // User : Es una clase que representa un usuario autenticado en la aplicación web
        // new User(claims.getSubject(), "", authorities) : Crea un objeto User con el nombre de usuario, la contraseña y los roles del usuario autenticado
        System.out.println("claims.getSubject(): " + claims.getSubject());
        // en los parámetros del constructor de User, el segundo parámetro es la contraseña, pero en este caso no se necesita porque no se va a utilizar la contraseña ya que se está utilizando un token JWT
        User princial = new User(claims.getSubject(), "", authorities);
        // UsernamePasswordAuthenticationToken : Es una clase que representa una autenticación basada en nombre de usuario y contraseña, nos sirve para autenticar a un usuario en la aplicación
        return new UsernamePasswordAuthenticationToken(princial, token, authorities);
    }

    public boolean validateToken(String token){
        try{
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}

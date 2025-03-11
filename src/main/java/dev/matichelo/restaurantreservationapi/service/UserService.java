package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.User;
import dev.matichelo.restaurantreservationapi.domain.enums.Role;
import dev.matichelo.restaurantreservationapi.dto.request.AuthRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.request.SignupRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.AuthResponseDTO;
import dev.matichelo.restaurantreservationapi.dto.response.UserProfileResponseDTO;
import dev.matichelo.restaurantreservationapi.exception.BadRequestException;
import dev.matichelo.restaurantreservationapi.exception.ResourceNotFoundException;
import dev.matichelo.restaurantreservationapi.mapper.UserMapper;
import dev.matichelo.restaurantreservationapi.repository.UserRepository;
import dev.matichelo.restaurantreservationapi.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional(readOnly = true)
    public AuthResponseDTO signIn(AuthRequestDTO authRequestDTO){
        // UsernamePasswordAuthenticationToken es una clase que extiende de AbstractAuthenticationToken y representa un token de autenticación basado en nombre de usuario y contraseña
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        // Authentication nos permite representar la información de autenticación de un principal ejemplo: nombre de usuario, contraseña, roles, etc.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createAccessToken(authentication);
        UserProfileResponseDTO profileResponseDTO = findByEmail(authRequestDTO.getEmail());
        return userMapper.toAuthResponseDTO(accessToken, profileResponseDTO);
    }

    @Transactional
    public UserProfileResponseDTO signup(SignupRequestDTO signupRequestDTO){
        boolean emailExists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if(emailExists){
            throw new BadRequestException("Email is already taken");
        }
        // to = a
        User user = userMapper.toUser(signupRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userMapper.toUserProfileResponseDTO(userRepository.save(user));
    }



    @Transactional(readOnly = true)
    public UserProfileResponseDTO findByEmail(String email){
        // ::new es una referencia a un constructor se usa así porque el método orElseThrow espera un Supplier y no un Consumer
        User user = userRepository.findOneByEmail(email).orElseThrow(ResourceNotFoundException::new);
        return userMapper.toUserProfileResponseDTO(user);
    }

}

package dev.matichelo.restaurantreservationapi.mapper;

import dev.matichelo.restaurantreservationapi.domain.entity.User;
import dev.matichelo.restaurantreservationapi.dto.request.SignupRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.AuthResponseDTO;
import dev.matichelo.restaurantreservationapi.dto.response.UserProfileResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toUser(SignupRequestDTO signupRequestDTO){
        return modelMapper.map(signupRequestDTO, User.class);
    }

    // Método para login y registro ya que es un DTO que se usa en ambos casos
    public UserProfileResponseDTO toUserProfileResponseDTO(User user){
        return modelMapper.map(user, UserProfileResponseDTO.class);
    }

    public AuthResponseDTO toAuthResponseDTO(String token, UserProfileResponseDTO userProfile){
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        authResponseDTO.setUser(userProfile);
        return authResponseDTO;
    }


}

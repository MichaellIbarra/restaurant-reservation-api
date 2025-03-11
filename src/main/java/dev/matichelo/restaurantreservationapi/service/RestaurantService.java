package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.Restaurant;
import dev.matichelo.restaurantreservationapi.dto.response.RestaurantResponseDTO;
import dev.matichelo.restaurantreservationapi.exception.ResourceNotFoundException;
import dev.matichelo.restaurantreservationapi.mapper.RestaurantMapper;
import dev.matichelo.restaurantreservationapi.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public Page<RestaurantResponseDTO> getAllRestaurants(Pageable pageable){
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        // .map es un método de Page que transforma cada elemento de la página en otro elemento usando una función
        // dentro de .map() , restaurantMapper es un objeto de la clase RestaurantMapper que se encarga de mapear un objeto de la clase Restaurant a un objeto de la clase RestaurantResponseDTO
        // restaurantMapper::toResponseDTO es una referencia a un método, es decir, es una forma de pasar un método como argumento a otro método
        return restaurants.map(restaurantMapper::toResponseDTO);
    }

    public Page<RestaurantResponseDTO> findByDistrictName(String districtName, Pageable pageable){
        Page<Restaurant> restaurants = restaurantRepository.findByDistrictName(districtName, pageable);
        return restaurants.map(restaurantMapper::toResponseDTO);
    }

    public RestaurantResponseDTO getRestaurantById(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(
                "Restaurant not found with id: " + id));
        return restaurantMapper.toResponseDTO(restaurant);
    }

}

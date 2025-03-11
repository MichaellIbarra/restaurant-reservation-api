package dev.matichelo.restaurantreservationapi.controller;

import dev.matichelo.restaurantreservationapi.dto.response.RestaurantResponseDTO;
import dev.matichelo.restaurantreservationapi.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/page")
    public ResponseEntity<Page<RestaurantResponseDTO>> getAllRestaurants(@PageableDefault(size = 5)Pageable pageable){
        Page<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/page/district")
    public ResponseEntity<Page<RestaurantResponseDTO>> findByDistrictName(@RequestParam String districtName,
                                                                          @PageableDefault(sort = "name", size = 5) Pageable pageable){
        // @PageableDefault es una anotación que se utiliza para configurar la paginación de los resultados de una
        // consulta., sus argumentos como sort es para ordenar los resultados en este caso por el nombre del
        // restaurante que comienza por la letra A, y size es para indicar el número de elementos que se mostrarán
        Page<RestaurantResponseDTO> restaurants = restaurantService.findByDistrictName(districtName, pageable);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id){
        RestaurantResponseDTO responseDTO = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(responseDTO);
    }

}

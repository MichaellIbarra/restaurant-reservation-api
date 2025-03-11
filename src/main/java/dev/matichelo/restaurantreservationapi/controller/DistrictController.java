package dev.matichelo.restaurantreservationapi.controller;

import dev.matichelo.restaurantreservationapi.dto.response.DistrictResponseDTO;
import dev.matichelo.restaurantreservationapi.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping
    public ResponseEntity<List<DistrictResponseDTO>> getAllDistricts(){
        List<DistrictResponseDTO> districts = districtService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

}

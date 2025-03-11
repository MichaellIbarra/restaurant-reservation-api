package dev.matichelo.restaurantreservationapi.mapper;

import dev.matichelo.restaurantreservationapi.domain.entity.District;
import dev.matichelo.restaurantreservationapi.dto.response.DistrictResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DistrictMapper {
    private final ModelMapper modelMapper;

    public DistrictResponseDTO toResponseDTO(District district){
        // modelMapper es para mapear objetos de una clase a otra
        return modelMapper.map(district, DistrictResponseDTO.class);
    }

    public List<DistrictResponseDTO> toResponseDTOList(List<District> districts){
        return districts.stream().map(this::toResponseDTO).toList();
    }

}

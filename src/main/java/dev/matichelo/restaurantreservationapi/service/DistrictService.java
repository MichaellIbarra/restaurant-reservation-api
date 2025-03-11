package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.District;
import dev.matichelo.restaurantreservationapi.dto.response.DistrictResponseDTO;
import dev.matichelo.restaurantreservationapi.mapper.DistrictMapper;
import dev.matichelo.restaurantreservationapi.repository.DistrictRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public List<DistrictResponseDTO> getAllDistricts(){
        List<District> districts = districtRepository.findAll();
        return districtMapper.toResponseDTOList(districts);
    }

}

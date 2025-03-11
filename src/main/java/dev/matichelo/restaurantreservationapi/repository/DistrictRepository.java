package dev.matichelo.restaurantreservationapi.repository;

import dev.matichelo.restaurantreservationapi.domain.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findByName(String name);
}

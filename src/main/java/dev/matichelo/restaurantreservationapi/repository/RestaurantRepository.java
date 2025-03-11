package dev.matichelo.restaurantreservationapi.repository;

import dev.matichelo.restaurantreservationapi.domain.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant  r WHERE r.district.name = :districtName")
    Page<Restaurant> findByDistrictName(@Param("districtName") String districtName, Pageable pageable);
}

package dev.matichelo.restaurantreservationapi.repository;

import dev.matichelo.restaurantreservationapi.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :clientId")
    List<Reservation> findByClientId(Long clientId);

    @Query("SELECT r FROM Reservation r WHERE r.id = :reservationId AND r.user.id = :userId")
    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);
}

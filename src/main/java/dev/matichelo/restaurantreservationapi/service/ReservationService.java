package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.Reservation;
import dev.matichelo.restaurantreservationapi.domain.entity.Restaurant;
import dev.matichelo.restaurantreservationapi.domain.entity.User;
import dev.matichelo.restaurantreservationapi.domain.enums.ReservationStatus;
import dev.matichelo.restaurantreservationapi.dto.request.ReservationRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.ReservationResponseDTO;
import dev.matichelo.restaurantreservationapi.exception.ResourceNotFoundException;
import dev.matichelo.restaurantreservationapi.mapper.ReservationMapper;
import dev.matichelo.restaurantreservationapi.repository.ReservationRepository;
import dev.matichelo.restaurantreservationapi.repository.RestaurantRepository;
import dev.matichelo.restaurantreservationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(authentication.getName()).orElseThrow( () -> new ResourceNotFoundException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        Reservation reservation = reservationMapper.toReservation(reservationRequestDTO);

        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.calculateTotalAmount();
        reservation = reservationRepository.save(reservation);

        return reservationMapper.toResponseDTO(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByClientId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Reservation> reservations = reservationRepository.findByClientId(user.getId());
        return reservationMapper.toResponseList(reservations);
    }

    @Transactional(readOnly = true)
    public ReservationResponseDTO getReservationById(Long reservationId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, user.getId()).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return reservationMapper.toResponseDTO(reservation);
    }

    public Reservation confirmReservationPayment(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setStatus(ReservationStatus.PAID);
        return reservationRepository.save(reservation);
    }


}

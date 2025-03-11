package dev.matichelo.restaurantreservationapi.mapper;

import dev.matichelo.restaurantreservationapi.domain.entity.Reservation;
import dev.matichelo.restaurantreservationapi.dto.request.ReservationRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.ReservationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationMapper {

    private final ModelMapper modelMapper;

    public Reservation toReservation(ReservationRequestDTO reservationRequestDTO){
        return modelMapper.map(reservationRequestDTO, Reservation.class);
    }

    public ReservationResponseDTO toResponseDTO(Reservation reservation){
        return modelMapper.map(reservation, ReservationResponseDTO.class);
    }

    public List<ReservationResponseDTO> toResponseList(List<Reservation> reservations){
        return reservations.stream().map(this::toResponseDTO).toList();
    }



}

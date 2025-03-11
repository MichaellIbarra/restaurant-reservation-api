package dev.matichelo.restaurantreservationapi.controller;

import dev.matichelo.restaurantreservationapi.dto.request.ReservationRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.ReservationResponseDTO;
import dev.matichelo.restaurantreservationapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// http://localhost:8080/api/v1/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody @Validated ReservationRequestDTO reservationRequestDTO){
        ReservationResponseDTO reservationResponseDTO = reservationService.createReservation(reservationRequestDTO);
        return new ResponseEntity<>(reservationResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByClientId()
    {
        List<ReservationResponseDTO> reservationResponseDTOS = reservationService.getReservationsByClientId();
        return new ResponseEntity<>(reservationResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationsByClientId(@PathVariable Long id)
    {
        ReservationResponseDTO reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}

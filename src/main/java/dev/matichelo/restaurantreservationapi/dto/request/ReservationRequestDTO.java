package dev.matichelo.restaurantreservationapi.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {
    private Long id;
    @NotNull(message = "Restaurant id is required")
    private Long restaurantId;
    @NotNull(message = "Reservation date is required")
    private LocalDateTime reservationDate;
    @Positive(message = "Number of people must be greater than 0")
    @Max(value = 20, message = "Number of people must be less than or equal to 20")
    private int numberOfPeople;
    private String additionalInfo;

}

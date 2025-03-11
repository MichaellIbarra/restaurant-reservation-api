package dev.matichelo.restaurantreservationapi.dto.paypal;

import lombok.Data;

@Data
public class Link {
    private String href; // href es la URL del recurso
    private String rel; // rel es la relación entre el recurso y el recurso actual
    private String method;
}

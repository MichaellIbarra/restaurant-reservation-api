package dev.matichelo.restaurantreservationapi.dto.response;

import lombok.Data;

@Data
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String districtName;
    private double pricePerPerson;
    private int capacity;
}

package dev.matichelo.restaurantreservationapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "price_per_person", nullable = false)
    private double pricePerPerson;
    @Column(name = "capacity", nullable = false)
    private int capacity;

    // La anotación @ManyToOne indica que la relación entre las entidades es de muchos a uno, es decir, que muchos restaurantes pueden estar en un distrito.
    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false, foreignKey = @ForeignKey(name = "fk_restaurant_district"))
    private District district;

}

package dev.matichelo.restaurantreservationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@SpringBootApplication
public class RestaurantReservationApiApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        String timeZone = TimeZone.getDefault().getID().toUpperCase();
        String currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("ZONA HORARIA CONFIGURADA: " + timeZone);
        System.out.println("FECHA Y HORA ACTUALES: " + currentDateTime);
        SpringApplication.run(RestaurantReservationApiApplication.class, args);
    }

}

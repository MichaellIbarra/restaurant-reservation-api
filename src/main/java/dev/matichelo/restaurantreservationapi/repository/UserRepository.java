package dev.matichelo.restaurantreservationapi.repository;

import dev.matichelo.restaurantreservationapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //  findOne es un método que se usa para buscar un usuario por su email la ventaja de usar One  solo va a
    //   devolver un usuario si existe en la base de datos
    Optional<User> findOneByEmail(String email);
    boolean existsByEmail(String email);
}

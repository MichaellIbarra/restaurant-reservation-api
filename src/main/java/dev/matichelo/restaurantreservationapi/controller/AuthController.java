package dev.matichelo.restaurantreservationapi.controller;

import dev.matichelo.restaurantreservationapi.dto.request.AuthRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.request.SignupRequestDTO;
import dev.matichelo.restaurantreservationapi.dto.response.AuthResponseDTO;
import dev.matichelo.restaurantreservationapi.dto.response.UserProfileResponseDTO;
import dev.matichelo.restaurantreservationapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody @Validated AuthRequestDTO authRequestDTO){
        AuthResponseDTO authResponseDTO = userService.signIn(authRequestDTO);
        // new ResponseEntity<>(authResponseDTO, HttpStatus.OK) : Crea una instancia de ResponseEntity con el cuerpo de la respuesta y el código de estado HTTP
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("sign-up")
    // @RequestBody : Anota un parámetro de método o un método de controlador y vincula el cuerpo de una solicitud web a un objeto del método
    // @Validated : Anota un tipo de argumento de método o un parámetro de método que debe ser validado
    // de debe poner el @Validated porque el SignupRequestDTO tiene anotaciones de validación
    public ResponseEntity<UserProfileResponseDTO> signUp(@RequestBody @Validated SignupRequestDTO signupRequestDTO){
        UserProfileResponseDTO userProfileResponseDTO = userService.signup(signupRequestDTO);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.CREATED);
    }

}

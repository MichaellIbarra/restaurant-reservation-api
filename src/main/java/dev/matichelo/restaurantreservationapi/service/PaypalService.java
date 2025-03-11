package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.Reservation;
import dev.matichelo.restaurantreservationapi.dto.paypal.*;
import dev.matichelo.restaurantreservationapi.exception.ResourceNotFoundException;
import dev.matichelo.restaurantreservationapi.repository.ReservationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaypalService {
    private final ReservationRepository reservationRepository;
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.api-base}")
    private String apiBase;
    private RestClient restClient;

    // PostConstruct es una anotación que se utiliza en un método que debe ejecutarse después de que se haya
    //  completado la inyección de dependencias para realizar cualquier inicialización.
    @PostConstruct
    public void init() {
        restClient = RestClient.builder().baseUrl(apiBase).build();
    }

    private String getAccessToken() {
        // MultiValueMap es una interfaz que extiende Map y se utiliza para representar un conjunto de valores para
        // una clave única.
        // diferencia entre Map y MultiValueMap es que Map no permite valores duplicados para una clave única,
        // mientras que MultiValueMap permite valores duplicados para una clave única.
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // add es un método que se utiliza para agregar un valor a la lista de valores para una clave única.
        // En este caso, se agrega el id del cliente y el secreto del cliente a la lista de valores para la clave
        // "grant_type".
        // grant_type es un parámetro que se utiliza para especificar el tipo de concesión que se está utilizando
        // para la autenticación.
        // En este caso, se está utilizando la concesión de credenciales de cliente.
        body.add("grant_type", "client_credentials");
        return Objects.requireNonNull(restClient.post()
                .uri("/v1/oauth2/token")
                // contentType es un método que se utiliza para especificar el tipo de contenido de la solicitud.
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                // header se utiliza para agregar un encabezado a la solicitud, en este caso se agrega el encabezado para la autorización.
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                // body se utiliza para especificar el cuerpo de la solicitud. En este caso, se especifica el cuerpo de la solicitud como un MultiValueMap.
                .body(body)
                // retrieve se utiliza para enviar la solicitud y recuperar la respuesta.
                .retrieve()
                // toEntity se utiliza para convertir la respuesta en una entidad de la clase especificada. En este caso, se convierte la respuesta en una entidad de la clase TokenResponse.
                // .class se usa para especificar la clase a la que se debe convertir la respuesta.
                .toEntity(TokenResponse.class)
                .getBody()).getAccessToken();
    }

    public OrderResponse createOrder(Long reservationId, String returnUrl, String cancelUrl){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIntent("CAPTURE");

        Amount amount = new Amount();
        amount.setCurrencyCode("USD");
        amount.setValue(reservation.getTotalAmount().toString());

        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setReferenceId(reservation.getId().toString());
        purchaseUnit.setAmount(amount);

        orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

        ApplicationContext applicationContext = ApplicationContext
                .builder()
                .brandName("Restaurant Reservation")
                .returnURL(returnUrl)
                .cancelURL(cancelUrl)
                .build();

        orderRequest.setApplicationContext(applicationContext);
        return restClient.post()
                .uri("/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(orderRequest)
                .retrieve()
                .toEntity(OrderResponse.class)
                // getBoy se utiliza para obtener el cuerpo de la respuesta.
                .getBody();

    }

    public OrderCaptureResponse captureOrder(String orderId){
        return restClient.post()
                .uri("v2/checkout/orders/{order_id}/capture", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(OrderCaptureResponse.class)
                .getBody();
                
    }

}

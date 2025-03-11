package dev.matichelo.restaurantreservationapi.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

// La anotación @Data es parte de Lombok y genera los getters y setters de los atributos de la clase
@Data
// La anotación @Builder es parte de Lombok y genera un constructor con todos los atributos de la clase, la dife
@Builder
public class ExperienceContext {
    @JsonProperty("payment_method_preference")
    private String paymentMethodPreference;
    @JsonProperty("brand_name")
    private String brandName;
    private String locale;
    @JsonProperty("landing_page")
    private String landingPage;
    @JsonProperty("user_action")
    private String userAction;
    @JsonProperty("return_url")
    private String returnURL;
    @JsonProperty("cancel_url")
    private String cancelURL;

}

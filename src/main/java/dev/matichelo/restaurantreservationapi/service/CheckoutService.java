package dev.matichelo.restaurantreservationapi.service;

import dev.matichelo.restaurantreservationapi.domain.entity.Reservation;
import dev.matichelo.restaurantreservationapi.dto.paypal.OrderCaptureResponse;
import dev.matichelo.restaurantreservationapi.dto.paypal.OrderResponse;
import dev.matichelo.restaurantreservationapi.dto.response.PaypalCaptureResponse;
import dev.matichelo.restaurantreservationapi.dto.response.PaypalOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final PaypalService paypalService;
    private final ReservationService reservationService;

    public PaypalOrderResponse createPaypalPaymentUrl(Long reservationId, String returnUrl, String cancelUrl){
        OrderResponse orderResponse = paypalService.createOrder(reservationId, returnUrl, cancelUrl);
        String paypalUrl = orderResponse.getLinks()
                .stream().filter(link -> link.getRel().equals("approve")).findFirst().orElseThrow(() -> new RuntimeException("No approve link found"))
                .getHref();
        return new PaypalOrderResponse(paypalUrl);
    }

    public PaypalCaptureResponse capturePaypalPayment(String orderId){
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean isCompleted = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaypalCaptureResponse paypalCaptureResponse = new PaypalCaptureResponse();
        paypalCaptureResponse.setCompleted(isCompleted);

        if(isCompleted){
            String purchaseIdStr =orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            // parse = convertir
            Reservation reservation = reservationService.confirmReservationPayment(Long.parseLong(purchaseIdStr));
            paypalCaptureResponse.setReservationId(reservation.getId());
        }
        return paypalCaptureResponse;
    }
}

package o.springback.controller.GestionCommande;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import o.springback.dto.PaymentRequest;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionCommande.Payment;
import o.springback.entities.GestionCommande.StatusPaiement;
import o.springback.services.GestionCommande.OrderServiceImpl;
import o.springback.services.GestionCommande.PaymentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderServiceImpl orderService;
    private final PaymentServiceImpl paymentService;

    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest request) {
        try {
            Order order = orderService.getOrderById(request.getOrderId());

            int amount = (int) (order.getTotalOrderPrice() * 1000); // convert to cents
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", "tnd");

            PaymentIntent intent = PaymentIntent.create(params);

            // Save the payment attempt
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount((long) amount);
            payment.setStripePaymentIntentId(intent.getId());
            payment.setStatus(StatusPaiement.PENDING); // Status initial
            payment.setCreatedAt(LocalDateTime.now());

            paymentService.createPaiement(payment);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());
            response.put("paymentIntentId", intent.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody Map<String, String> payload) {
        String intentId = payload.get("paymentIntentId");
        String status = payload.get("status");

        Payment payment = paymentService.findByStripePaymentIntentId(intentId);

        payment.setStatus(StatusPaiement.valueOf(status.toUpperCase()));
        paymentService.updatePaiement(payment.getId(), payment);

        return ResponseEntity.ok().build();
    }
}

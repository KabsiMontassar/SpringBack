package o.springback.controller.GestionCommande;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import o.springback.dto.PaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/create-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", request.getAmount());
            params.put("currency", "usd");

            PaymentIntent intent = PaymentIntent.create(params);

            // Return as proper JSON object
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // Other endpoints as needed
}
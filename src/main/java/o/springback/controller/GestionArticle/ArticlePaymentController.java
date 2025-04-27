package o.springback.controller.GestionArticle;
import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionArticle.IPaymentService;
import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.PaymentArticle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class ArticlePaymentController {

    private final IPaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentArticle>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentArticle> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentArticle> createPayment(@RequestBody PaymentArticle payment) {
        PaymentArticle saved = paymentService.save(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentArticle> updatePayment(@PathVariable Long id, @RequestBody PaymentArticle payment) {
        payment.setId(id);
        PaymentArticle updated = paymentService.update(payment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}

package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.PaymentArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentArticle,Long> {
}

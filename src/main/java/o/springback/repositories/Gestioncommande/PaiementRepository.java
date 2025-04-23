package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Payment, Long> {
    Payment findByStripePaymentIntentId(String intentId);
}
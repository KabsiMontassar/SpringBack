package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Payment;

import java.util.List;

public interface IPaiementService {
    Payment createPaiement(Payment payment);
    Payment updatePaiement(Long id, Payment payment);
    void deletePaiement(Long id);
    Payment getPaiementById(Long id);
    List<Payment> getAllPaiements();

    //String checkPaymentStatus(Long id);
}
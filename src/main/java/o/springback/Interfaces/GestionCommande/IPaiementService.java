package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Paiement;

import java.util.List;

public interface IPaiementService {
    Paiement createPaiement(Paiement paiement);
    Paiement updatePaiement(Long id, Paiement paiement);
    void deletePaiement(Long id);
    Paiement getPaiementById(Long id);
    List<Paiement> getAllPaiements();

    //String checkPaymentStatus(Long id);
}

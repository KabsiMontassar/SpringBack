package o.springback.services.GestionCommande;

import com.stripe.model.PaymentIntent;
import o.springback.Interfaces.GestionCommande.IPaiementService;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionCommande.Payment;
import o.springback.entities.GestionCommande.StatusPaiement;
import o.springback.repositories.Gestioncommande.OrderRepo;
import o.springback.repositories.Gestioncommande.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaiementService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaiementRepository paymentRepo;


    @Autowired
    private PaiementRepository paiementRepository;

    @Override
    public Payment createPaiement(Payment payment) {
        return paiementRepository.save(payment);
    }

    @Override
    public Payment updatePaiement(Long id, Payment payment) {
        payment.setId(id);
        return paiementRepository.save(payment);
    }

    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }

    @Override
    public Payment getPaiementById(Long id) {
        return paiementRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Payment findByStripePaymentIntentId(String intentId) {
        return paiementRepository.findByStripePaymentIntentId(intentId);
    }
}
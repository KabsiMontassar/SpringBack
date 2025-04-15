package o.springback.controller.GestionCommande;

import o.springback.entities.GestionCommande.Paiement;
import o.springback.services.GestionCommande.PaiementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiement")
public class PaymentController {
    @Autowired
    private PaiementServiceImpl paiementService;

    @PostMapping("/add")
    public Paiement createPaiement(@RequestBody Paiement paiement) {
        return paiementService.createPaiement(paiement);
    }

    @PutMapping("/update/{id}")
    public Paiement updatePaiement(@PathVariable Long id, @RequestBody Paiement paiement) {
        return paiementService.updatePaiement(id, paiement);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
    }

    @GetMapping("/retrieve/{id}")
    public Paiement getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id);
    }

    @GetMapping("/retrieve-all")
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }
}
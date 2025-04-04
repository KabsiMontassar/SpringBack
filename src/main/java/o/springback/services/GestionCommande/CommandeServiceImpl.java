package o.springback.services.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.ICommandeService;
import o.springback.entities.GestionCommande.Commande;
import o.springback.entities.GestionCommande.LigneCommande;
import o.springback.entities.GestionCommande.StatusCommande;
import o.springback.entities.GestionProduits.Products;
import o.springback.repositories.GestionProduitsRepository.ProductRepository;
import o.springback.repositories.Gestioncommande.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements ICommandeService {
    @Autowired
    private CommandeRepository commandeRepository;


    @Autowired
    private ProductRepository productRepo;

    @Override
    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }
    @Override
    public Commande creerCommande(Long clientId, Map<Long, Integer> produitsQuantites) {
        // 1. Vérifier stocks
        produitsQuantites.forEach((prodId, qte) -> {
            Products p = productRepo.findById(prodId)
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
            if (p.getQuantiteDisponible() < qte) {
                throw new RuntimeException("Stock insuffisant pour " + p.getNom());
            }
        });

        // 2. Créer commande
        Commande cmd = new Commande();
        cmd.setDateCommande(LocalDate.now());
        cmd.setStatus(StatusCommande.EN_COURS);

        // 3. Ajouter lignes
        produitsQuantites.forEach((prodId, qte) -> {
            Products p = productRepo.findById(prodId).get();
            LigneCommande ligne = new LigneCommande(p, qte);
            cmd.addLigne(ligne);
            // Mise à jour stock
            p.setQuantiteDisponible(p.getQuantiteDisponible() - qte);
        });

        cmd.calculerTotal();
        return commandeRepository.save(cmd);
    }

    public void updateStatus(Long idCommande, StatusCommande newStatus) {
        Commande cmd = commandeRepository.findById(idCommande).orElseThrow();
        cmd.setStatus(newStatus);
        // Envoyer notification si nécessaire
    }

    @Override
    public Commande updateCommande(Long id, Commande commande) {
        commande.setIdCommande(id);
        return commandeRepository.save(commande);
    }

    @Override
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}
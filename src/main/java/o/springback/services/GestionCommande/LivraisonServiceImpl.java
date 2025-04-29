package o.springback.services.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.ILivraisonService;
import o.springback.Interfaces.GestionCommande.OrderService;
import o.springback.entities.GestionCommande.Livraison;
import o.springback.entities.GestionCommande.Order;
import o.springback.repositories.Gestioncommande.LivraisonRepository;
import o.springback.repositories.Gestioncommande.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LivraisonServiceImpl implements ILivraisonService {
    @Autowired
    private LivraisonRepository livraisonRepository;
    private OrderRepo orderRepo;




    @Override
    public Livraison updateLivraison(Long id, Livraison updatedLivraison) {
        Livraison existing = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        existing.setAdresse(updatedLivraison.getAdresse());
        existing.setDescription(updatedLivraison.getDescription());
        existing.setLatitude(updatedLivraison.getLatitude());
        existing.setLongitude(updatedLivraison.getLongitude());
        return livraisonRepository.save(existing);
    }


    @Override
    public void deleteLivraison(Long id) {
        livraisonRepository.deleteById(id);
    }

    @Override
    public Livraison getLivraisonById(Long id) {
        return livraisonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    @Override
    public Livraison affecterLivraisonToOrder(Livraison l, Long idOrder) {
        Order order = orderRepo.findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (order.getLivraison() != null) {
            throw new RuntimeException("Cette commande a déjà une livraison.");
        }

        l.setOrder(order);
        Livraison livraison = livraisonRepository.save(l);
        order.setLivraison(livraison);
        orderRepo.save(order);
        return livraison;
    }

    @Override
    public Livraison getLivraisonByCommandeId(Long commandeId)
    {
        Order order = orderRepo.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        return order.getLivraison();
    }


}
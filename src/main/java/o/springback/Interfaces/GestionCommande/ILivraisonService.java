package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Livraison;

import java.util.List;

public interface ILivraisonService {
    Livraison createLivraison(Livraison livraison);
    Livraison updateLivraison(Long id, Livraison livraison);
    void deleteLivraison(Long id);
    Livraison getLivraisonById(Long id);
    List<Livraison> getAllLivraisons();
}

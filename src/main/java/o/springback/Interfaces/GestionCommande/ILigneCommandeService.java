package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.LigneCommande;

import java.util.List;

public interface ILigneCommandeService {
    LigneCommande createLigneCommande(LigneCommande ligneCommande);
    LigneCommande updateLigneCommande(Long id, LigneCommande ligneCommande);
    void deleteLigneCommande(Long id);
    LigneCommande getLigneCommandeById(Long id);
    List<LigneCommande> getAllLigneCommandes();
}

package o.springback.Interfaces.GestionCommande;


import o.springback.entities.GestionCommande.Facture;

import java.util.List;

public interface IFactureService {
    Facture createFacture(Facture facture);
    Facture updateFacture(Long id, Facture facture);
    void deleteFacture(Long id);
    Facture getFactureById(Long id);
    List<Facture> getAllFactures();

}

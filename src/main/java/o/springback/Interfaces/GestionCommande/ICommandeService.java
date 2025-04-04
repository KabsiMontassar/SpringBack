package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Commande;

import java.util.List;
import java.util.Map;

public interface ICommandeService {
    Commande createCommande(Commande commande);
    Commande creerCommande(Long clientId, Map<Long, Integer> produitsQuantites);
    Commande updateCommande(Long id, Commande commande);
    void deleteCommande(Long id);
    Commande getCommandeById(Long id);
    List<Commande> getAllCommandes();



    //Commande annulerCommande(Commande commande);
    //List<Commande> getCommandesByClientId(Long clientId);
    //double calculTotal(List<Commande> items);
    //boolean validerCommande(List<Commande> items);

}

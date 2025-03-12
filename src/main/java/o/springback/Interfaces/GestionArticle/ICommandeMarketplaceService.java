package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.CommandeMarketplace;

import java.util.List;

public interface ICommandeMarketplaceService {
    List<CommandeMarketplace> findAll();
    CommandeMarketplace findById(Long idCommandeMarketplace);
    CommandeMarketplace save(CommandeMarketplace commandeMarketplace);
    CommandeMarketplace update(CommandeMarketplace commandeMarketplace);
    void delete(Long idCommandeMarketplace);
}

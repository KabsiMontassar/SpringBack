package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.ICommandeMarketplaceService;
import o.springback.entities.GestionArticle.CommandeMarketplace;
import o.springback.repositories.GestionArticle.AvisArticleRepository;
import o.springback.repositories.GestionArticle.CommandeMarketplaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeMarketplaceService implements ICommandeMarketplaceService {
    private final CommandeMarketplaceRepository commandeMarketplaceRepository;

    @Override
    public List<CommandeMarketplace> findAll() {
        return commandeMarketplaceRepository.findAll();
    }

    @Override
    public CommandeMarketplace findById(Long idCommandeMarketplace) {
        return commandeMarketplaceRepository.findById(idCommandeMarketplace).orElse(null);
    }

    @Override
    public CommandeMarketplace save(CommandeMarketplace commandeMarketplace) {
        return commandeMarketplaceRepository.save(commandeMarketplace);

    }

    @Override
    public CommandeMarketplace update(CommandeMarketplace commandeMarketplace) {
        return commandeMarketplaceRepository.save(commandeMarketplace);
    }

    @Override
    public void delete(Long idCommandeMarketplace) {
        commandeMarketplaceRepository.deleteById(idCommandeMarketplace);

    }
}

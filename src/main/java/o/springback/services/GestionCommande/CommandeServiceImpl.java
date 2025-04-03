package o.springback.services.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.ICommandeService;
import o.springback.entities.GestionCommande.Commande;
import o.springback.repositories.Gestioncommande.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements ICommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
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
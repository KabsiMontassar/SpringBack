package o.springback.services.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.ILigneCommandeService;
import o.springback.entities.GestionCommande.LigneCommande;
import o.springback.repositories.Gestioncommande.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LigneCommandeServiceImpl implements ILigneCommandeService {
    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Override
    public LigneCommande createLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }

    @Override
    public LigneCommande updateLigneCommande(Long id, LigneCommande ligneCommande) {
        ligneCommande.setIdLigne(id);
        return ligneCommandeRepository.save(ligneCommande);
    }

    @Override
    public void deleteLigneCommande(Long id) {
        ligneCommandeRepository.deleteById(id);
    }

    @Override
    public LigneCommande getLigneCommandeById(Long id) {
        return ligneCommandeRepository.findById(id).orElse(null);
    }

    @Override
    public List<LigneCommande> getAllLigneCommandes() {
        return ligneCommandeRepository.findAll();
    }


}

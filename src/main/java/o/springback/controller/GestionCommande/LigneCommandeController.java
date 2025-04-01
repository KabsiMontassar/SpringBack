package o.springback.controller.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.entities.GestionCommande.LigneCommande;
import o.springback.services.GestionCommande.LigneCommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lignePanier")
public class LigneCommandeController {
    @Autowired
    private LigneCommandeServiceImpl ligneCommandeService;

    @PostMapping("/add")
    public LigneCommande createLigneCommande(@RequestBody LigneCommande ligneCommande) {
        return ligneCommandeService.createLigneCommande(ligneCommande);
    }

    @PutMapping("update/{id}")
    public LigneCommande updateLigneCommande(@PathVariable Long id, @RequestBody LigneCommande ligneCommande) {
        return ligneCommandeService.updateLigneCommande(id, ligneCommande);
    }

    @DeleteMapping("delete/{id}")
    public void deleteLigneCommande(@PathVariable Long id) {
        ligneCommandeService.deleteLigneCommande(id);
    }

    @GetMapping("retrieve/{id}")
    public LigneCommande getLigneCommandeById(@PathVariable Long id) {
        return ligneCommandeService.getLigneCommandeById(id);
    }

    @GetMapping("retrieve-all")
    public List<LigneCommande> getAllLigneCommandes() {
        return ligneCommandeService.getAllLigneCommandes();
    }
}

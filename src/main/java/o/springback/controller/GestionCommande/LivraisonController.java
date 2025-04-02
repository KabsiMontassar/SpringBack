package o.springback.controller.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.entities.GestionCommande.Livraison;
import o.springback.services.GestionCommande.LivraisonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/livraison")
public class LivraisonController {
    @Autowired
    private LivraisonServiceImpl livraisonService;

    @PostMapping("/add")
    public Livraison createLivraison(@RequestBody Livraison livraison) {
        return livraisonService.createLivraison(livraison);
    }

    @PutMapping("/update/{id}")
    public Livraison updateLivraison(@PathVariable Long id, @RequestBody Livraison livraison) {
        return livraisonService.updateLivraison(id, livraison);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLivraison(@PathVariable Long id) {
        livraisonService.deleteLivraison(id);
    }

    @GetMapping("/retrieve/{id}")
    public Livraison getLivraisonById(@PathVariable Long id) {
        return livraisonService.getLivraisonById(id);
    }

    @GetMapping("/retrieve-all")
    public List<Livraison> getAllLivraisons() {
        return livraisonService.getAllLivraisons();
    }
}
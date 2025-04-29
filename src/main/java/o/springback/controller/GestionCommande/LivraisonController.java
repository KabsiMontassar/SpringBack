package o.springback.controller.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.ILivraisonService;
import o.springback.dto.GestionCommande.LivraisonDTO;
import o.springback.entities.GestionCommande.Livraison;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/livraison")
public class LivraisonController {

    private ILivraisonService livraisonService;

    @PostMapping
    public ResponseEntity<Livraison> createLivraison(@RequestBody LivraisonDTO dto) {
        Livraison livraison = new Livraison();
        livraison.setAdresse(dto.getAdresse());
        livraison.setDescription(dto.getDescription());
        livraison.setLatitude(dto.getLatitude());
        livraison.setLongitude(dto.getLongitude());

        livraisonService.affecterLivraisonToOrder(livraison, dto.getCommandeId());
        return ResponseEntity.ok(livraison);
    }


    @PutMapping("/update/{id}")
    public Livraison updateLivraison(@PathVariable Long id, @RequestBody Livraison livraison) {
        return livraisonService.updateLivraison(id, livraison);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLivraison(@PathVariable Long id) {
        livraisonService.deleteLivraison(id);
    }

    @GetMapping("/{id}")
    public Livraison getLivraisonById(@PathVariable Long id) {
        return livraisonService.getLivraisonById(id);
    }

    @GetMapping("/retrieve-all")
    public List<Livraison> getAllLivraisons() {
        return livraisonService.getAllLivraisons();
    }

    @GetMapping("/commande/{commandeId}")
    public Livraison getLivraisonByCommandeId(@PathVariable Long commandeId) {

        return livraisonService.getLivraisonByCommandeId(commandeId);

    }

}
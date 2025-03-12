package o.springback.controller.GestionCommande;

import lombok.AllArgsConstructor;
import o.springback.entities.GestionCommande.Commande;
import o.springback.services.GestionCommande.CommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commande")
public class CommandeController {
    @Autowired
    private CommandeServiceImpl commandeService;

    @PostMapping("add")
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.createCommande(commande);
    }

    @PutMapping("update/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        return commandeService.updateCommande(id, commande);
    }

    @DeleteMapping("delete/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }

    @GetMapping("get-by-id/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id);
    }

    @GetMapping("get-all")
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }
}

package o.springback.controller.GestionCommande;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/facture")
public class FactureController {
    @Autowired
    private FactureServiceImpl factureService;

    @PostMapping("/add")
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.createFacture(facture);
    }

    @PutMapping("/update/{id}")
    public Facture updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        return factureService.updateFacture(id, facture);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }

    @GetMapping("/retrieve/{id}")
    public Facture getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id);
    }

    @GetMapping("/retrieve-all")
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }
}

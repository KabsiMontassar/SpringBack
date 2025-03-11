package o.springback.controller.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IAvisProductService;
import o.springback.entities.GestionProduits.AvisProduct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/avis")
public class AvisProductController {
    private final IAvisProductService avisService;

    @GetMapping("/all")
    public List<AvisProduct> getAllAvis() {
        return avisService.findAll();
    }

    @GetMapping("/{idAvis}")
    public AvisProduct getAvisById(@PathVariable Long idAvis) {
        return avisService.findById(idAvis);
    }

    @PostMapping("/add")
    public AvisProduct addAvis(@RequestBody AvisProduct avis) {
        return avisService.save(avis);
    }

    @PutMapping("/update")
    public AvisProduct updateAvis(@RequestBody AvisProduct avis) {
        return avisService.update(avis);
    }

    @DeleteMapping("/delete/{idAvis}")
    public void deleteAvis(@PathVariable Long idAvis) {
        avisService.delete(idAvis);
    }
}

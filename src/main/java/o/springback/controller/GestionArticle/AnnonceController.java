package o.springback.controller.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IAnnonceService;
import o.springback.entities.GestionArticle.Annonce;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/annonce")
public class AnnonceController {
    private final IAnnonceService annonceService;

    @GetMapping("/allAnnonce")
    public List<Annonce> getAllAnnonce() {
        return annonceService.findAll();
    }

    @GetMapping("/{idAnnonce}")
    public Annonce getAnnonceById(@PathVariable Long idAnnonce) {
        return annonceService.findById(idAnnonce);
    }

    @PostMapping("/addAnnonce")
    public Annonce addAnnonce(@RequestBody Annonce annonce) {
        return annonceService.save(annonce);
    }

    @PutMapping("/updateAnnonce")
    public Annonce updateAnnonce(@RequestBody Annonce annonce) {
        return annonceService.update(annonce);
    }

    @DeleteMapping("/deleteAnnonce/{idAnnonce}")
    public void deleteAnnonce(@PathVariable Long idAnnonce) {
        annonceService.delete(idAnnonce);
    }

}

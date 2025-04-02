package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tache")
public class TacheController {

    ITacheService tacheService;
    @GetMapping("/retrieve-all-Taches")
    public List<Tache> getTaches() {
        return tacheService.findAll();
    }
    @GetMapping("/retrieve-Tache/{Tache-id}")
    public Tache retrieveTache(@PathVariable("Tache-id") Long TacheId) {
        return tacheService.findById(TacheId);
    }
    @PostMapping("/add-Tache")
    public Tache addTache(@RequestBody Tache c) {
        return tacheService.save(c);
    }
    @DeleteMapping("/remove-Tache/{Tache-id}")
    public void removeTache(@PathVariable("Tache-id") Long TacheId) {
        tacheService.delete(TacheId);
    }
    @PutMapping("/update-Tache")
    public Tache updateTache(@RequestBody Tache c) {
        return tacheService.update(c);
    }
}
package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.PeriodeHistorique;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/tache")
public class TacheController {

    ITacheService tacheService;
    @GetMapping("/retrieve-all-taches")
    public List<Tache> getTaches() {
        return tacheService.findAll();
    }
    @GetMapping("/retrieve-tache/{Tache-id}")
    public Tache retrieveTache(@PathVariable("Tache-id") Long TacheId) {
        return tacheService.findById(TacheId);
    }
    @PostMapping("/add-tache")
    public Tache addTache(@RequestBody Tache c) {
        return tacheService.save(c);
    }
    @DeleteMapping("/remove-tache/{Tache-id}")
    public void removeTache(@PathVariable("Tache-id") Long TacheId) {
        tacheService.delete(TacheId);
    }
    @PutMapping("/update-tache")
    public Tache updateTache(@RequestBody Tache c) {
        return tacheService.update(c);
    }
    @GetMapping("/nombre-taches-par-employe/{id}")
    public Map<String, Object> getNombreTachesParEmploye(@PathVariable Long id){
        return tacheService.getNombreTachesParEmploye(id);
    }
    @GetMapping("/nombre-taches-par-statut/{employeeId}")
    public Map<String, Long> getNombreTachesParStatut(@PathVariable Long employeeId) {
            return tacheService.getNombreTachesParStatut(employeeId);
    }
    @GetMapping("/tache-par-statut/{employeeId}/{statut}")
    public Map<String, Object> getTachesParStatut(@PathVariable Long employeeId, @PathVariable StatutTache statut){
        Long count = tacheService.getTachesParStatut(employeeId, statut);
        Map<String, Object> response = new HashMap<>();
        response.put("statut", statut);
        response.put("Nombre Taches", count);
        return response;
    }
    @GetMapping("/historique/{employeeId}")
    public Map<String, Object> getHistorique(@PathVariable Long employeeId, @RequestParam PeriodeHistorique periode) {
        return tacheService.getHistoriqueTachesParPeriode(employeeId, periode);
    }
}
package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;
import o.springback.entities.GestionPlanningEmployee.PeriodeHistorique;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/tache")
public class TacheController {

    ITacheService tacheService;
    @Autowired
    IPlanningService planningService;
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
        return tacheService.add(c);
    }
    @PostMapping("/add-sous-tache/{parentId}")
    public Tache ajouterSousTache(@PathVariable Long parentId, @RequestBody Tache sousTache){
        return tacheService.ajouterSousTache(parentId, sousTache);
    }
    @DeleteMapping("/remove-tache/{Tache-id}")
    public void removeTache(@PathVariable("Tache-id") Long TacheId) {
        tacheService.delete(TacheId);
    }
    @DeleteMapping("/remove-sous-tache/{id}")
    public void removeSousTache(@PathVariable Long id){
        tacheService.deletesoustache(id);
    }
    @PutMapping("/update-tache/{id}")
    public Tache updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        return tacheService.update(id, tache);
    }
    @PutMapping("/update-sous-tache/{id}")
    public Tache updateSousTache(@PathVariable Long id, @RequestBody Tache sousTache){
        return tacheService.updateSousTache(id, sousTache);
    }

    @GetMapping("/has-sous-taches/{id}")
    public boolean hasSousTache (@PathVariable Long id){
        return tacheService.hasSousTaches(id);
    }
    @GetMapping("/descendants/{id}")
    public List<Tache> getAllDescendants(@PathVariable Long id){
        Tache tache = tacheService.findById(id);
        return (tache != null) ? tacheService.getAllDescendants(tache) : Collections.emptyList();
    }
    @GetMapping("/descendants/count/{id}")
    public Map<String, Object> countAllDescendants(@PathVariable Long id){
        Tache tache = tacheService.findById(id);
        if (tache == null){
            return Map.of("message", "Tâche introuvable", "count", 0);
        }
        int count = tacheService.countAllDescendants(tache);
        return Map.of(
                "idTache", id,
                "titre", tache.getTitre(),
                "nombreSousTaches", count
        );
    }
    @GetMapping("/progression/{tacheId}")
    public Map<String, Object> getProgressionTache(@PathVariable Long tacheId){
        return tacheService.getProgressionTache(tacheId);
    }
    @GetMapping("/progression-employe/{employeeId}")
    public Map<String, Object> getProgressionParEmployee(@PathVariable Long employeeId) {
        return tacheService.getProgressionParEmploye(employeeId);
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
    @GetMapping("/employee/{employeeId}")
    public List<Tache> getTachesByEmployee(@PathVariable Long employeeId) {
        return tacheService.getTachesByEmployeeId(employeeId);
    }

    @GetMapping("/historique/{employeeId}")
    public Map<String, Object> getHistorique(@PathVariable Long employeeId, @RequestParam PeriodeHistorique periode) {
        return tacheService.getHistoriqueTachesParPeriode(employeeId, periode);
    }
    @GetMapping("/historiqueDate/{employeeId}")
    public Map<String, Object> getHistoriqueTaches(@PathVariable Long employeeId, @RequestParam String periode) {
        return tacheService.getHistoriqueTachesParDate(employeeId, periode);
    }
    @PutMapping("/replanifier/{employeeId}")
    public Map<String, Object> replanifierTaches ( @PathVariable Long employeeId, @RequestParam String strategie) {
        return tacheService.replanifierTache(employeeId, strategie);
    }


}
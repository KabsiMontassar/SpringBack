package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.PeriodeHistorique;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import java.util.List;
import java.util.Map;

public interface ITacheService {
    Tache add(Tache tache);
    Tache ajouterSousTache(Long parentId, Tache sousTache);
    Tache update(Long id, Tache tache);
    Tache updateSousTache(Long id, Tache sousTache);
    void delete(Long id);
    void deletesoustache(Long id);
    Boolean hasSousTaches(Long idTache);
    List<Tache> getAllDescendants(Tache tache);
    int countAllDescendants(Tache tache);
    Map<String, Object> getProgressionTache(Long tacheId);
    Map<String, Object> getProgressionParEmploye(Long employeeId);
    Tache findById(Long id);
    List<Tache> findAll();
    // ITacheService.java
    List<Tache> getTachesByEmployeeId(Long idEmployee);

    Map<String, Object> getNombreTachesParEmploye(Long employeeId);
    Map<String, Long> getNombreTachesParStatut(Long employeeId); //pour afficher tout les statuts
    Long getTachesParStatut(Long employeeId, StatutTache statut); // pour afficher kol statut wahdou
    Map<String, Object> getHistoriqueTachesParPeriode(Long employeeId, PeriodeHistorique periode);
    Map<String, Object> getHistoriqueTachesParDate(Long  employeeId, String periode);
    Map<String, Object> replanifierTache(Long employeeId, String strategie);
    int findMaxPositionByStatut(StatutTache statut);
    Map<String, Object> getStatsGlobales();
}
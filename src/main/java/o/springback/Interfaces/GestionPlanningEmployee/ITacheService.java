package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.PeriodeHistorique;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import java.util.List;
import java.util.Map;

public interface ITacheService {
    Tache save(Tache tache);
    Tache update(Tache tache);
    void delete(Long id);
    Tache findById(Long id);
    List<Tache> findAll();
    Map<String, Object> getNombreTachesParEmploye(Long employeeId);
    Map<String, Long> getNombreTachesParStatut(Long employeeId); //pour afficher tout les statuts
    Long getTachesParStatut(Long employeeId, StatutTache statut); // pour afficher kol statut wahdou
    Map<String, Object> getHistoriqueTachesParPeriode(Long employeeId, PeriodeHistorique periode);
    Map<String, Object> getHistoriqueTachesParDate(Long  employeeId, String periode);
}
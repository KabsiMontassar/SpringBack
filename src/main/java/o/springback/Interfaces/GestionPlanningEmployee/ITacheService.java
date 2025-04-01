package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.Tache;
import java.util.List;
import java.util.Map;

public interface ITacheService {
    Tache save(Tache tache);
    Tache update(Tache tache);
    void delete(Long id);
    Tache findById(Long id);
    List<Tache> findAll();

    //Map<Long, Long> getNombreTachesParEmploye();
}

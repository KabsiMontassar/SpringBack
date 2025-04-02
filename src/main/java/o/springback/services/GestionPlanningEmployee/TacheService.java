package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TacheService implements ITacheService{
    private TacheRepository tacheRepository;
    @Override
    public Tache save(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public Tache update(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public void delete(Long id) {
        tacheRepository.deleteById(id);
    }

    @Override
    public Tache findById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tache> findAll() {
        return tacheRepository.findAll();
    }

    @Override
    public Map<String, Object> getNombreTachesParEmploye(Long employeeId) {
        List<Object[]> results = tacheRepository.countTachesByEmployee(employeeId);
        if (results.isEmpty()) return null;
        Object [] result = results.get(0);
        String nom = (String) result[0];
        String prenom = (String) result[1];
        Long count = (Long) result[2];
        Map<String, Object> map = new HashMap<>();
        map.put("Nom", nom);
        map.put("Prénom ", prenom);
        map.put("Nombre de taches ", count);

        return map;
    }

    @Override
    public Map<String, Long> getNombreTachesParStatut(Long employeeId) {
        List<Object[]> result = tacheRepository.countTachesByStatutTacheForEmployee(employeeId);
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] statutCount : result ) {
            StatutTache statut = (StatutTache) statutCount[0];
            long count = (Long) statutCount[1];
            map.put(statut.name(), count);
        }
        return map;
    }

    @Override
    public Long getTachesParStatut(Long employeeId, StatutTache statut) {
        List<Object[]> status = tacheRepository.countTachesByStatutTacheForEmployee(employeeId);
        for (Object[] stat: status) {
            StatutTache currentStatut = (StatutTache) stat[0];
            Long count = (Long) stat[1];
            if (currentStatut.equals(statut)) {
                return count;
            }
        }
        return 0L; //aucune tâche trouvée
    }

}
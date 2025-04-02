package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.Tache;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
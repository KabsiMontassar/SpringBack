package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanningService implements IPlanningService{
    private PlanningRepository planningRepository;
    @Override
    public Planning save(Planning planning) {
        return planningRepository.save(planning);
    }
    @Override
    public Planning update(Planning planning) {
        return planningRepository.save(planning);
    }
    @Override
    public void delete(Long id) {
        planningRepository.deleteById(id);
    }
    @Override
    public Planning findById(Long id) {
        return planningRepository.findById(id).orElse(null);
    }
    @Override
    public List<Planning> findAll() {
        return planningRepository.findAll();
    }


}
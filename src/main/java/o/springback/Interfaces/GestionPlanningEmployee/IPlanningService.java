package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import java.util.List;
import java.util.Map;

public interface IPlanningService {
    Planning save(Planning planning);
    Planning update(Planning planning);
    void delete(Long id);
    Planning findById(Long id);
    List<Planning> findAll();
    Planning addPlanningForEmployee(Long employeeId, Planning planning);
    Planning updatePlanningForEmployee(Long employeeId, Planning planning);


}
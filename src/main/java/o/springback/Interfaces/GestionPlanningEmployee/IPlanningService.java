package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import java.util.List;
public interface IPlanningService {
    Planning save(Planning planning);
    Planning update(Planning planning);
    void delete(Long id);
    Planning findById(Long id);
    List<Planning> findAll();
}

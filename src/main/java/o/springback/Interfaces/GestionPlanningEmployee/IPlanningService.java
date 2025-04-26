package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.entities.GestionPlanningEmployee.TypePlanning;

import java.util.Date;
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
    Map<TypePlanning, Long> getDureeAbsenceParType(Long employeeId);
    List<Planning> getPlanningsByEmployeeId(Long employeeId);
    List<Planning> findPlanningsBetween(Long employeeID, Date weekStart, Date weekEnd);
    List<Planning> getEmployeePlanningForDay(Long employeeId, Date day);
    List<Planning> findPlanningsByType(TypePlanning type);
}
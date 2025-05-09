package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.entities.GestionPlanningEmployee.*;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class PlanningService implements IPlanningService{
    private PlanningRepository planningRepository;
    private TacheRepository tacheRepository;
    private EmployeeRepository employeeRepository;
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

    @Override
    public Planning addPlanningForEmployee(Long employeeId, Planning planning) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec ID: " + employeeId));
        planning.setEmployee(employee);
        return planningRepository.save(planning);
    }

    @Override
    public Planning updatePlanningForEmployee(Long employeeId, Planning planning) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec ID: " + employeeId));
        planning.setEmployee(employee);
        return planningRepository.save(planning);
    }

    @Override
    public Map<TypePlanning, Long> getDureeAbsenceParType(Long employeeId) {
        List<Planning> plannings = planningRepository.findByEmployeeId(employeeId);
        Map<TypePlanning, Long> dureeAbsence = new HashMap<>();

        for (Planning planning : plannings){
            TypePlanning typeA = planning.getTypePlanning();
            LocalDate debut = convertToLocalDate(planning.getDateDebut());
            LocalDate fin = convertToLocalDate(planning.getDateFin());

            long jours = ChronoUnit.DAYS.between(debut, fin) +1;
            dureeAbsence.put(typeA, dureeAbsence.getOrDefault(typeA, 0L) + jours);
        }

        return dureeAbsence;
    }

    @Override
    public List<Planning> getPlanningsByEmployeeId(Long employeeId) {
        return planningRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Planning> findPlanningsBetween(Long employeeID, Date weekStart, Date weekEnd) {
        return planningRepository.findEmployeePlanningsInRange(employeeID, weekStart, weekEnd);
    }

    //@Override
    //public List<Planning> findPlanningsBetween(Date start, Date end) {
    //    return planningRepository.findPlanningsInRange(start, end);
    //}

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate(); //conversion de LocalDate en java.sql.Date
    }

}



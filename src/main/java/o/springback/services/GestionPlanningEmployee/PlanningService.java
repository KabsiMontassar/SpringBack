package o.springback.services.GestionPlanningEmployee;
import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlanningService implements IPlanningService{
    private PlanningRepository planningRepository;
    private TacheRepository tacheRepository;
    private EmployeeRepository employeeRepository;
    @Override
    public Planning save(Planning planning) {
        if(planning.getDateFin().before(planning.getDateDebut())) {
            throw new IllegalArgumentException("your date choice is unvalide");
        }
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
    @Transactional
    public Planning addPlanningForEmployee(Long employeeId, Planning planning) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec ID: " + employeeId));
        if(planning.getDateDebut() == null || planning.getDateFin() == null) {
            throw new IllegalArgumentException("you have to specify a date range for " + planning.getTypePlanning());
        }
        if (planning.getDateFin().before(planning.getDateDebut())){
            throw new IllegalArgumentException("your date choice is invalid");
        }
        if (planning.isTimeSpecific()) {
            if(planning.getStartTime() == null || planning.getEndTime() == null) {
                throw new IllegalArgumentException("You need to specify a time range for this " + planning.getTypePlanning());
            }
            if (planning.getEndTime().isBefore(planning.getStartTime())) {
                throw new IllegalArgumentException("Your choice of time range is invalid");
            }
        }
        planning.setEmployee(employee);
        if (planning.getEmployee() != null &&planning.getEmployee().getIdEmployee() !=null){
            List<Planning> conflits = planningRepository.findEmployeePlanningsInRange(
                    planning.getEmployee().getIdEmployee(),
                    planning.getDateDebut(), planning.getDateFin()
            );
            if(planning.getIdPlanning() != null) {
                conflits = conflits.stream()
                        .filter(p -> !p.getIdPlanning().equals(planning.getIdPlanning()))
                        .collect(Collectors.toList());
            }
            if (!conflits.isEmpty()) {
                throw new IllegalStateException("Employee already has planning(s) in this date range:"
                        +conflits.stream().map(p -> p.getTypePlanning() + "(" + p.getDateDebut() + "to" +p.getDateFin() +")")
                        .collect(Collectors.joining(", ")));
            }

        }
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

    @Override
    public List<Planning> getEmployeePlanningForDay(Long employeeId, Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);

        // Set to start of day (00:00:00)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // Set to end of day (23:59:59.999)
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        return planningRepository.findByEmployeeAndDay(employeeId, day, startOfDay, endOfDay);
    }

    @Override
    public List<Planning> findPlanningsByType(TypePlanning type) {
        return planningRepository.findByTypePlanningOrderByDateDebut(type);
    }

    //@Override
    //public List<Planning> findPlanningsBetween(Date start, Date end) {
    //    return planningRepository.findPlanningsInRange(start, end);
    //}

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate(); //conversion de LocalDate en java.sql.Date
    }

}



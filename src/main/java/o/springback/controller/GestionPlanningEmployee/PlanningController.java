package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;
import o.springback.Interfaces.GestionPlanningEmployee.IEmployeeService;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import o.springback.entities.GestionPlanningEmployee.Employee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.entities.GestionPlanningEmployee.TypePlanning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketOption;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/planning")
public class PlanningController {

    IPlanningService planningService;
    PlanningRepository planningRepository;
    @Autowired
    IEmployeeService employeeService;

    @GetMapping("/retrieve-all-Plannings")
    public List<Planning> getPlannings() {
        return planningService.findAll();
    }

    @GetMapping("/retrieve-Planning/{Planning-id}")
    public Planning retrievePlanning(@PathVariable("Planning-id") Long PlanningId) {
        return planningService.findById(PlanningId);
    }
    @PostMapping("/add-Planning")
    public Planning addPlanning(@RequestBody Planning c) {
        return planningService.save(c);
    }

    @DeleteMapping("/remove-Planning/{Planning-id}")
    public void removePlanning(@PathVariable("Planning-id") Long PlanningId) {
        planningService.delete(PlanningId);
    }
    @PutMapping("/update-Planning")
    public Planning updatePlanning(@RequestBody Planning c) {
        return planningService.update(c);
    }
    @PostMapping("/add-employee-planning/{employeeId}")
    public Planning addEmployeePlanning(@PathVariable Long employeeId, @RequestBody Planning planning) {
        return planningService.addPlanningForEmployee(employeeId, planning);
    }
    @PutMapping("/update-employee-planning/{employeeId}")
    public Planning updateEmployeePlanning(@PathVariable Long employeeId, @RequestBody Planning planning) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employé introuvable avec l'ID " + employeeId);
        }
        planning.setEmployee(employee);
        return planningService.update(planning);
    }
    @GetMapping("/duree-absence-par-type/{employeeId}")
    public Map<TypePlanning, Long> getDureeAbsenceParType(@PathVariable Long employeeId) {
        return planningService.getDureeAbsenceParType(employeeId);
    }
    @GetMapping("/employee-planning/{employeeId}")
    public List<Planning> getPlanningsByEmployee(@PathVariable Long employeeId) {
        return planningService.getPlanningsByEmployeeId(employeeId);
    }
    @GetMapping("/employee/{employeeId}/week")
    public List<Planning> getPlanningsInRange(@PathVariable Long employeeId,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekStart,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekEnd
                                              ) {
        try {
            return planningService.findPlanningsBetween(employeeId, weekStart, weekEnd);
        } catch (Exception e) {
            throw new RuntimeException("Employee not found with ID: " + employeeId, e);
        }

    //        @RequestParam Date start,
    //        @RequestParam Date end
    //) {
    //    System.out.println("Start date: " + start);
    //    System.out.println("End date: " + end);
    //    return planningService.findPlanningsBetween(start, end);
    }

    @GetMapping("/{employeeId}/daily-plannings")
    public ResponseEntity<List<Planning>> getDailyPlannings(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date day) {

        List<Planning> plannings = planningService.getEmployeePlanningForDay(employeeId, day);
        return ResponseEntity.ok(plannings);
    }


}
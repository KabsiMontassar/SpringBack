package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;
import o.springback.Interfaces.GestionPlanningEmployee.IEmployeeService;

import o.springback.entities.GestionPlanningEmployee.Employee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/planning")
public class PlanningController {

    IPlanningService planningService;
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
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employé introuvable avec l'ID " + employeeId);
        }
        planning.setEmployee(employee);
        return planningService.save(planning);
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
}
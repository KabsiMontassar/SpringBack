package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IEmployeeService;
import o.springback.dto.EmployeeDTO;
import o.springback.entities.GestionPlanningEmployee.Employee;
import o.springback.entities.GestionPlanningEmployee.TypePost;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    IEmployeeService employeeService;
    PlanningRepository planningRepository;

    @GetMapping("/retrieve-all-Employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/retrieve-Employee/{Employee-id}")
    public Employee retrieveEmployee(@PathVariable("Employee-id") Long EmployeeId) {
        return employeeService.findById(EmployeeId);
    }
    @PostMapping("/add-Employee")
    public Employee addEmployee(@RequestBody Employee c) {
        return employeeService.save(c);
    }

    @DeleteMapping("/remove-Employee/{Employee-id}")
    public void removeEmployee(@PathVariable("Employee-id") Long EmployeeId) {
        employeeService.delete(EmployeeId);
    }
    @PutMapping("/update-Employee")
    public Employee updateEmployee(@RequestBody Employee c) {
        return employeeService.update(c);
    }
    @GetMapping("/type-post")
    public List<EmployeeDTO> getEmployeeByTypePost(@RequestParam TypePost type) {
        return employeeService.findByTypePost(type);
    }

}

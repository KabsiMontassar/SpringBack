package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IEmployeeService;
import o.springback.entities.GestionPlanningEmployee.Employee;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

        IEmployeeService employeeService;

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
}

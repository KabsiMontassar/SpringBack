package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IEmployeeService;
import o.springback.entities.GestionPlanningEmployee.Employee;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.entities.GestionPlanningEmployee.TypePost;
import o.springback.repositories.GestionPlanningEmployeeRepository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import o.springback.dto.EmployeeDTO;
@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<EmployeeDTO> findByTypePost(TypePost type) {
        //return employeeRepository.findByTypePost(type);
        List<Employee> employees = employeeRepository.findByTypePost(type);
        return employees.stream().map(emp -> new EmployeeDTO(
                emp.getIdEmployee(),
                emp.getNom(),
                emp.getPrenom(),
                emp.getEmail(),
                emp.getTelephone(),
                emp.getTypePoste().name(),
                emp.getSalaire()
        )).collect(Collectors.toList());
    }

}

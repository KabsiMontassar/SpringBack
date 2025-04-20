package o.springback.Interfaces.GestionPlanningEmployee;
import o.springback.dto.EmployeeDTO;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.entities.GestionPlanningEmployee.TypePost;
import org.hibernate.boot.model.internal.PkDrivenByDefaultMapsIdSecondPass;
import org.springframework.stereotype.Service;
import o.springback.entities.GestionPlanningEmployee.Employee;
import java.util.List;

public interface IEmployeeService {

    Employee save(Employee employee);
    Employee update(Employee employee);
    void delete(Long id);
    Employee findById(Long id);
    List<Employee> findAll();
    List<EmployeeDTO> findByTypePost (TypePost type);
}
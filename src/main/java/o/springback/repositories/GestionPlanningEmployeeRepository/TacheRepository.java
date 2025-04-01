package o.springback.repositories.GestionPlanningEmployeeRepository;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long>{
    @Query("SELECT t.employee.idEmployee, COUNT(t) FROM t WHERE  t.employee IS NOT NULL GROUP BY t.employee.idEmployee")
    List<Object[]> countTachesParEmploye();
}

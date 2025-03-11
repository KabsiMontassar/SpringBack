package o.springback.repositories.GestionPlanningEmployeeRepository;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long>{
}

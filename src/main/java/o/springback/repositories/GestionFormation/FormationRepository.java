package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
    @EntityGraph(attributePaths = "detailFormation")
    List<Formation> findAll();

}

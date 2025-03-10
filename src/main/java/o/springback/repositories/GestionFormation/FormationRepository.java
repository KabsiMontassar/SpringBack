package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
}

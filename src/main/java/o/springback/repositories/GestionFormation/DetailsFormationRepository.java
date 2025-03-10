package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.DetailsFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsFormationRepository extends JpaRepository<DetailsFormation, Integer> {
}
package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
}
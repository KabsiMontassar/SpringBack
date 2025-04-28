package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.ParticipationStatus;
import o.springback.entities.GestionUser.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationStatusRepository extends JpaRepository<ParticipationStatus, Integer> {

    Optional<ParticipationStatus> findByUserAndFormation(User user, Formation formation);
}

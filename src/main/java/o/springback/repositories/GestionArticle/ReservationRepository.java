package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}

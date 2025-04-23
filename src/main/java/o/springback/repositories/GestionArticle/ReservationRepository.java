package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByArticleId(Long articleId);
}

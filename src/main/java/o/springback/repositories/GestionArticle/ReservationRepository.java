package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByArticleId(Long articleId);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.article WHERE r.id = :id")
    Optional<Reservation> findByIdWithArticle(@Param("id") Long id);
    List<Reservation> findByUserIdUser(Long userId);

}

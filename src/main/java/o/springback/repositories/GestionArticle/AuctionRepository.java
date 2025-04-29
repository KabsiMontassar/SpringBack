package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.PaymentArticle;
import o.springback.entities.GestionArticle.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    List<Auction> findByIsActiveFalseAndPaymentStatusNotAndEndTimeBefore(PaymentArticle.Status status, LocalDateTime time);

    List<Auction> findByIsActiveFalseAndPaymentIsNull();
    @Query("SELECT a FROM Auction a LEFT JOIN a.bids b GROUP BY a ORDER BY COUNT(b) DESC")
    List<Auction> findTop5ByBidsCount();

    List<Auction> findByArticleId(Long articleId);
    @Query("SELECT a FROM Auction a JOIN FETCH a.article WHERE a.id = :id")
    Optional<Auction> findByIdWithArticle(@Param("id") Long id);

    // zeydin



}

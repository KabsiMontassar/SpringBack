package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    List<Auction> findByIsActiveFalseAndPaymentStatusNotAndEndTimeBefore(Payment.Status status, LocalDateTime time);

    List<Auction> findByIsActiveFalseAndPaymentIsNull();
    @Query("SELECT a FROM Auction a LEFT JOIN a.bids b GROUP BY a ORDER BY COUNT(b) DESC")
    List<Auction> findTop5ByBidsCount();

    List<Auction> findByArticleId(Long articleId);


}

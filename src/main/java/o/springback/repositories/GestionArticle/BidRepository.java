package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findByAuctionArticleId(Long articleId);
    List<Bid> findByAuctionId(Long auctionId);
    
    @Query("SELECT b FROM Bid b JOIN b.auction a JOIN a.article ar WHERE ar.user.idUser = :userId")
    List<Bid> findByUserId(Long userId);
    
    @Query("SELECT b FROM Bid b JOIN b.auction a JOIN a.article ar WHERE ar.user.idUser = :userId ORDER BY b.bidAmount DESC")
    List<Bid> findBidsByUserIdOrderByAmountDesc(Long userId);

    @Query("SELECT DISTINCT b.auction.article FROM Bid b " +
            "WHERE b.user.idUser = :userId " +
            "AND b.auction.isActive = false " +
            "AND b.bidAmount = (SELECT MAX(b2.bidAmount) FROM Bid b2 WHERE b2.auction = b.auction)")
    List<Article> findWonArticlesByUserId(@Param("userId") Long userId);
}

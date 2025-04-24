package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findByAuctionArticleId(Long articleId);
    List<Bid> findByAuctionId(Long auctionId);


}

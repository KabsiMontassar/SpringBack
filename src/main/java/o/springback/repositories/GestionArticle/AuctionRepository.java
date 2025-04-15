package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
}

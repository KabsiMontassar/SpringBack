package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
}

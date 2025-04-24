package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {
    List<Article> findByTypeArticle(Payment.PaymentType typeArticle);

    @Query("SELECT a FROM Article a WHERE a.auction.id = :auctionId")
    Article findByAuctionId(Long auctionId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Article a WHERE a.title = :title")
    boolean existsByTitle(@Param("title") String title);
    @Query("SELECT a FROM Article a WHERE a.title = :title")
    Article findByTitle(@Param("title") String title);

}

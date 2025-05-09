package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.PaymentArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {
    List<Article> findByTypeArticle(PaymentArticle.PaymentType typeArticle);

    @Query("SELECT a FROM Article a WHERE a.auction.id = :auctionId")
    Article findByAuctionId(Long auctionId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Article a WHERE a.title = :title")
    boolean existsByTitle(@Param("title") String title);
    @Query("SELECT a FROM Article a WHERE a.title = :title")
    Article findByTitle(@Param("title") String title);
    List<Article> findByTitleContainingIgnoreCase(String title);

    List<Article> findByIsAvailableAndTypeArticle(boolean isAvailable, PaymentArticle.PaymentType typeArticle);
/*

    @Query("SELECT u, " +
            "(SELECT COUNT(a1) FROM Article a1 WHERE a1.user = u AND a1.category = o.springback.entities.GestionArticle.Article.CategoryAU.commun) * 5 + " +
            "(SELECT COUNT(a2) FROM Article a2 WHERE a2.user = u AND a2.category = o.springback.entities.GestionArticle.Article.CategoryAU.advanced) * 10 + " +
            "(SELECT COUNT(a3) FROM Article a3 WHERE a3.user = u AND a3.category = o.springback.entities.GestionArticle.Article.CategoryAU.vip) * 20 AS score " +
            "FROM User u JOIN u.articles a " +
            "GROUP BY u " +
            "ORDER BY score DESC")
    List<Object[]> findUsersWithArticleScores();

    @Query("SELECT a, " +
            "(SELECT COUNT(a1) FROM Article a1 WHERE a1.user = a.user AND a1.category = o.springback.entities.GestionArticle.Article.CategoryAU.commun) * 5 + " +
            "(SELECT COUNT(a2) FROM Article a2 WHERE a2.user = a.user AND a2.category = o.springback.entities.GestionArticle.Article.CategoryAU.advanced) * 10 + " +
            "(SELECT COUNT(a3) FROM Article a3 WHERE a3.user = a.user AND a3.category = o.springback.entities.GestionArticle.Article.CategoryAU.vip) * 20 AS userScore " +
            "FROM Article a " +
            "ORDER BY userScore DESC")
    List<Object[]> findArticlesOrderedByUserScore();*/
}

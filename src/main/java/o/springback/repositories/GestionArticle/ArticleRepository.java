package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {
    List<Article> findByTypeArticle(Payment.PaymentType typeArticle);

}

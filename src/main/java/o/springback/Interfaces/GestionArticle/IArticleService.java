package o.springback.Interfaces.GestionArticle;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Payment;

import java.util.List;

public interface IArticleService {
    List<Article> findAll();
    Article findById(Long idArticle);
    Article save(Article article);
    Article update(Article article);
    void delete(Long idArticle);
    List<Article> findByTypeArticle(Payment.PaymentType typeArticle);

}

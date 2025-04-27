package o.springback.Interfaces.GestionArticle;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.PaymentArticle;

import java.util.List;

public interface IArticleService {
    List<Article> findAll();
    Article findById(Long idArticle);
    Article save(Article article);
    Article update(Article article);
    void delete(Long idArticle);
    List<Article> findByTypeArticle(PaymentArticle.PaymentType typeArticle);
    void AffectAuctionToArticle(Long idArticle, Long idAuction);
    List<Article> searchByTitle(String title);
    List<Article> findByIsAvailableAndTypeArticle(boolean available, PaymentArticle.PaymentType type);
}

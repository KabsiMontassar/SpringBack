package o.springback.Interfaces.GestionArticle;
import o.springback.entities.GestionArticle.Article;

import java.util.List;

public interface IArticleService {
    List<Article> findAll();
    Article findById(Long idArticle);
    Article save(Article article);
    Article update(Article article);
    void delete(Long idArticle);
}

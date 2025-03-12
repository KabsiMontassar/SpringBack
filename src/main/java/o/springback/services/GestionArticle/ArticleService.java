package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.repositories.GestionArticle.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ArticleService implements IArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(Long idArticle) {
        return articleRepository.findById(idArticle).orElse(null);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Long idArticle) {
        articleRepository.deleteById(idArticle);
    }
}

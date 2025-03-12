package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {
}

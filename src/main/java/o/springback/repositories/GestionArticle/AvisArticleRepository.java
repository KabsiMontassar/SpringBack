package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.AvisArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisArticleRepository extends JpaRepository<AvisArticle,Long> {
}

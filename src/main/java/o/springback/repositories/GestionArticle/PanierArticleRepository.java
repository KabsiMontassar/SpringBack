package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.PanierArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierArticleRepository extends JpaRepository<PanierArticle,Long> {
}

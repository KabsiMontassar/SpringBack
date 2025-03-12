package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
}

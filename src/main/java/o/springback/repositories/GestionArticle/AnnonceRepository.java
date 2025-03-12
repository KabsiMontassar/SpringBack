package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {
}

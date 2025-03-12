package o.springback.repositories.GestionArticle;

import o.springback.entities.GestionArticle.CommandeMarketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeMarketplaceRepository extends JpaRepository<CommandeMarketplace,Long> {
}

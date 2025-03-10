package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LignePanierRepository extends JpaRepository<LignePanier, Long> {
}

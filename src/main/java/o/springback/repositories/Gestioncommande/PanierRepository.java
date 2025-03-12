package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
}

package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
}

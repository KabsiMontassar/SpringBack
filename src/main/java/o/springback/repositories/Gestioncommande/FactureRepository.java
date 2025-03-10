package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}

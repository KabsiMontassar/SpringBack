package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
}

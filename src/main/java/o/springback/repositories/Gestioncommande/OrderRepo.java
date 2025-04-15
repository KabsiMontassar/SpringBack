package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}

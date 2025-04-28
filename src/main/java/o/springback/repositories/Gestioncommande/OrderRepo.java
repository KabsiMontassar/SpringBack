package o.springback.repositories.Gestioncommande;

import o.springback.entities.GestionCommande.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
        List<Order> findByStatus(String status);
}

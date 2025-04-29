package o.springback.repositories.Gestioncommande;
import o.springback.dto.GestionCommande.TopProductDTO;

import o.springback.dto.GestionCommande.TopProductDTO;
import o.springback.entities.GestionCommande.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT new o.springback.dto.GestionCommande.TopProductDTO(op.pk.products.nom, SUM(op.quantity)) " +
            "FROM OrderProduct op GROUP BY op.pk.products.nom ORDER BY SUM(op.quantity) DESC")
    List<TopProductDTO> findTopSellingProducts(Pageable pageable);
}
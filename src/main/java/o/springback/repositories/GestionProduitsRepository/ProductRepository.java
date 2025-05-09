package o.springback.repositories.GestionProduitsRepository;

import o.springback.entities.GestionProduits.Products;
import o.springback.entities.GestionUser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    @Query("SELECT p FROM Products p JOIN p.categorie c " +
            "WHERE c IN (SELECT pi.product.categorie FROM ProductInteraction pi WHERE pi.user = :user) " +
            "ORDER BY p.dateAjout DESC")
    List<Products> findPreferredProductsForUser(@Param("user") User user);

}

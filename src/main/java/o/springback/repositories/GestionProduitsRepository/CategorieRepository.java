package o.springback.repositories.GestionProduitsRepository;

import o.springback.entities.GestionProduits.CategorieProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<CategorieProduct, Long> {
}

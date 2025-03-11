package o.springback.repositories.GestionProduitsRepository;

import o.springback.entities.GestionProduits.AvisProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisProductRepository extends JpaRepository<AvisProduct, Long> {}

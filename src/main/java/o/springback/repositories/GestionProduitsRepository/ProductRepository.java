package o.springback.repositories.GestionProduitsRepository;

import o.springback.entities.GestionProduits.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> { }

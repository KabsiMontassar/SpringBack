package o.springback.repositories.GestionProduitsRepository;

import o.springback.entities.GestionProduits.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {}

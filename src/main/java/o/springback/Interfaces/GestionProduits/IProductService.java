package o.springback.Interfaces.GestionProduits;

import o.springback.entities.GestionProduits.Products;
import java.util.List;

public interface IProductService {
    List<Products> findAll();
    Products findById(Long idProduit);
    Products save(Products product);
    Products update(Products product);
    void delete(Long idProduit);
    void dailyProductSummary();
}

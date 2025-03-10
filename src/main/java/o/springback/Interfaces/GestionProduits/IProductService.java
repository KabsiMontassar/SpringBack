package o.springback.Interfaces.GestionProduits;
import o.springback.entities.GestionProduits.Products;

import java.util.List;

public interface IProductService {
    Products findById(Long idProduit);
    List<Products> findAll();
    Products save(Products p);
    void delete(Long idProduits);
    Products update(Products p);
}

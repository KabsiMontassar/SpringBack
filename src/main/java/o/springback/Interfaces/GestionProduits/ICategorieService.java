package o.springback.Interfaces.GestionProduits;

import o.springback.entities.GestionProduits.CategorieProduct;

import java.util.List;

public interface ICategorieService {
    List<CategorieProduct> findAll();
    CategorieProduct findById(Long idCategorie);
    CategorieProduct save(CategorieProduct categorie);
    CategorieProduct update(CategorieProduct categorie);
    void delete(Long idCategorie);
}

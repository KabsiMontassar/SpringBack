package o.springback.Interfaces.GestionProduits;

import o.springback.entities.GestionProduits.AvisProduct;

import java.util.List;

public interface IAvisProductService {
    List<AvisProduct> findAll();
    AvisProduct findById(Long idAvis);
    AvisProduct save(AvisProduct avis);
    AvisProduct update(AvisProduct avis);
    void delete(Long idAvis);
}

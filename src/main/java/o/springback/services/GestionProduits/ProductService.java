package o.springback.services.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IProductService;
import o.springback.entities.GestionProduits.Products;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    @Override
    public Products findById(Long idProduit) {
        return null;
    }

    @Override
    public List<Products> findAll() {
        return null;
    }

    @Override
    public Products save(Products p) {
        return null;
    }

    @Override
    public void delete(Long idProduits) {

    }

    @Override
    public Products update(Products p) {
        return null;
    }
}

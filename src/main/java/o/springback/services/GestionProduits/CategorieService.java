package o.springback.services.GestionProduits;


import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.ICategorieService;
import o.springback.entities.GestionProduits.CategorieProduct;
import o.springback.repositories.GestionProduitsRepository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategorieService implements ICategorieService {
    private final CategorieRepository categorieRepository;

    @Override
    public List<CategorieProduct> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public CategorieProduct findById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    @Override
    public CategorieProduct save(CategorieProduct categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public CategorieProduct update(CategorieProduct categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}

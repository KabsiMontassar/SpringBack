package o.springback.services.GestionProduits;


import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IAvisProductService;
import o.springback.entities.GestionProduits.AvisProduct;
import o.springback.repositories.GestionProduitsRepository.AvisProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvisProductService implements IAvisProductService{
    private final AvisProductRepository avisRepository;

    @Override
    public List<AvisProduct> findAll() {
        return avisRepository.findAll();
    }

    @Override
    public AvisProduct findById(Long idAvis) {return avisRepository.findById(idAvis).orElse(null);}

    @Override
    public AvisProduct save(AvisProduct avis) {
        return null;
    }

    @Override
    public AvisProduct update(AvisProduct avis) {
        return avisRepository.save(avis);
    }

    @Override
    public void delete(Long idAvis) {
        avisRepository.deleteById(idAvis);
    }
}

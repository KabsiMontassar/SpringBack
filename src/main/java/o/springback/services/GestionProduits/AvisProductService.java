package o.springback.services.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IAvisProductService;
import o.springback.entities.GestionProduits.AvisProduct;
import o.springback.entities.GestionProduits.Products;
import o.springback.repositories.GestionProduitsRepository.AvisProductRepository;
import o.springback.repositories.GestionProduitsRepository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvisProductService implements IAvisProductService {

    private final AvisProductRepository avisRepository;
    private final ProductRepository productRepository;

    @Override
    public List<AvisProduct> findAll() {
        return avisRepository.findAll();
    }

    @Override
    public AvisProduct findById(Long idAvis) {
        return avisRepository.findById(idAvis).orElse(null);
    }

    @Override
    public AvisProduct save(AvisProduct avis) {
        if (avis.getProduit() != null && avis.getProduit().getIdProduit() != null) {
            Products existingProduct = productRepository.findById(avis.getProduit().getIdProduit())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id : " + avis.getProduit().getIdProduit()));
            avis.setProduit(existingProduct);
            return avisRepository.save(avis);
        } else {
            throw new RuntimeException("Produit manquant ou idProduit null");
        }
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

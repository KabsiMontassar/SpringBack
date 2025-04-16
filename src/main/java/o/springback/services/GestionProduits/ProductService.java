package o.springback.services.GestionProduits;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.springback.Interfaces.GestionProduits.IProductService;
import o.springback.entities.GestionProduits.Products;
import o.springback.repositories.GestionProduitsRepository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Products> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Products findById(Long idProduit) {
        return productRepository.findById(idProduit).orElse(null);
    }

    @Override
    public Products save(Products product) {
        return productRepository.save(product);
    }

    @Override
    public Products update(Products product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Scheduled(cron = "*/15 * * * * ?")
    @Override
    public void dailyProductSummary() {
        List<Products> products = productRepository.findAll();

        Products produitPlusPopulaire = null;
        Double prixMax = (double) 0;

        for (Products product : products) {
            if (product.getPrix() > prixMax) {
                prixMax = product.getPrix();
                produitPlusPopulaire = product;
            }
        }

        if (produitPlusPopulaire != null) {
            for (Products product : products) {
                if (product.getIdProduit().equals(produitPlusPopulaire.getIdProduit())) {
                    product.setPrix((float) (product.getPrix() * 0.8f)); // 20% de réduction
                    productRepository.save(product);
                } else {
                    product.setPrix((float) (product.getPrix() * 0.95f)); // 5% de réduction
                    productRepository.save(product);
                }
            }

            log.info("Le produit le plus cher est " + produitPlusPopulaire.getNom() + 
                    " avec un prix de " + prixMax);
        }
    }
}

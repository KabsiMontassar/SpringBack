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
        try {
            List<Products> products = productRepository.findAll();
            Products produitPlusPopulaire = null;
            Double prixMax = 0.0;

            for (Products product : products) {
                if (product.getPrix() > prixMax) {
                    prixMax = product.getPrix();
                    produitPlusPopulaire = product;
                }
            }

            if (produitPlusPopulaire != null) {
                log.info("=== Rapport des produits ===");
                log.info("Nombre total de produits: " + products.size());
                log.info("Produit le plus cher: " + produitPlusPopulaire.getNom());
                log.info("Prix: " + prixMax);
                log.info("ID: " + produitPlusPopulaire.getIdProduit());
                log.info("========================");
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'exécution du rapport des produits", e);
        }
    }
}



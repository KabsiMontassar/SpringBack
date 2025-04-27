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

    //@Scheduled(fixedDelay = 10000)
    @Scheduled(fixedDelay = 7889400) 

    @Override
    public void deleteOutOfStockProducts() {
        try {
            List<Products> products = productRepository.findAll();
            for (Products product : products) {
                if (product.getQuantiteDisponible() <= 0) {
                    productRepository.delete(product);
                    log.info("Produit supprimé automatiquement (hors stock) - ID: " + product.getIdProduit() + ", Nom: " + product.getNom());
                }
            }
        } catch (Exception e) {
            log.error("Erreur lors de la suppression des produits hors stock", e);
        }
    }
}



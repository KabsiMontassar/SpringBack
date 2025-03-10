package o.springback.controller.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IProductService;
import o.springback.entities.GestionProduits.Products;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/produits")
public class ProductController {

    IProductService productService;

    @GetMapping("/retrieve-all-Produits")
    public List<Products> getProducts() {
        return productService.findAll();
    }
    @GetMapping("/retrieve-Produit/{idProduit}")
    public Products retrieveProducts(@PathVariable("idProduit") Long idProduit) {
        return productService.findById(idProduit);
    }
    @PostMapping("/add-Produits")
    public Products addProducts(@RequestBody Products p) {
        return productService.save(p);
    }
    @DeleteMapping("/remove-Produit/{idProduit}")
    public void removeProducts(@PathVariable("idProduit") Long idProduit) {
        productService.delete(idProduit);
    }
    @PutMapping("/update-Produits")
    public Products updateProducts(@RequestBody Products p) {
        return productService.update(p);
    }
}

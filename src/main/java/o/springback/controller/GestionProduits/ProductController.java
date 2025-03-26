package o.springback.controller.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IProductService;
import o.springback.entities.GestionProduits.Products;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/produits")

@RestController
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/retrieve-all-Produits")
    public List<Products> getProduits() {
        return productService.findAll();
    }

    @GetMapping("/retrieve-Produits/{idProduit}")
    public Products retrieveProduits(@PathVariable("idProduit") Long idProduit) {
        return productService.findById(idProduit);}

    @PostMapping("/add-Produits")
    public Products save(@RequestBody Products p) {
        return productService.save(p);
    }

    @PutMapping("/update")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public Products updateProduct(@RequestBody Products p) {
        return productService.update(p);
    }

    @DeleteMapping("/delete/{idProduit}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("idProduit") Long idProduit) {
        productService.delete(idProduit);
    }
}


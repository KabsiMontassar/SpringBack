package o.springback.controller.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.ICategorieService;
import o.springback.entities.GestionProduits.CategorieProduct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")


public class CategorieProductController {
    private final ICategorieService categorieService;

    @GetMapping("/all")
    public List<CategorieProduct> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/{idCategorie}")
    public CategorieProduct getCategorieById(@PathVariable Long idCategorie) {
        return categorieService.findById(idCategorie);
    }

    @PostMapping("/add")
    public CategorieProduct addCategorie(@RequestBody CategorieProduct categorie) {
        return categorieService.save(categorie);
    }

    @PutMapping("/update")
    public CategorieProduct updateCategorie(@RequestBody CategorieProduct categorie) {
        return categorieService.update(categorie);
    }

    @DeleteMapping("/delete/{idCategorie}")
    public void deleteCategorie(@PathVariable Long idCategorie) {
        categorieService.delete(idCategorie);
    }
}

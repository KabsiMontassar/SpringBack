package o.springback.controller.GestionProduits;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IStockService;
import o.springback.entities.GestionProduits.Stock;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final IStockService stockService;

    @GetMapping("/all")
    public List<Stock> getAllStocks() {
        return stockService.findAll();
    }

    @GetMapping("/{idStock}")
    public Stock getStockById(@PathVariable Long idStock) {
        return stockService.findById(idStock);
    }

    @PostMapping("/add")
    public Stock addStock(@Valid @RequestBody Stock stock) {
        return (Stock) stockService.save(stock);
    }

    @PutMapping("/update")
    public Stock updateStock(@Valid @RequestBody Stock stock) {
        return stockService.update(stock);
    }

    @DeleteMapping("/delete/{idStock}")
    public void deleteStock(@PathVariable Long idStock) {
        stockService.delete(idStock);
    }
}
package o.springback.services.GestionProduits;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionProduits.IStockService;
import o.springback.entities.GestionProduits.Stock;
import o.springback.repositories.GestionProduitsRepository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class StockService implements IStockService {
    private final StockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long idStock) {
        return stockRepository.findById(idStock).orElseThrow(() -> new RuntimeException("Stock not found with ID: " + idStock));}

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock update(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void delete(Long id) {
        stockRepository.deleteById(id);
    }
}
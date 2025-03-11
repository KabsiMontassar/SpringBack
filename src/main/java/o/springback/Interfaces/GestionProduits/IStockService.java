package o.springback.Interfaces.GestionProduits;

import o.springback.entities.GestionProduits.Stock;

import java.util.List;

public interface IStockService {
    List<Stock> findAll();
    Stock findById(Long idStock);
    Object save(Stock stock);
    Stock update(Stock stock);
    void delete(Long idStock);
}

package o.springback.services.GestionCommande;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionCommande.OrderProductService;
import o.springback.entities.GestionCommande.OrderProduct;
import o.springback.repositories.Gestioncommande.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }

    /*
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public OrderProduct createLigneCommande(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct updateLigneCommande(Long id, OrderProduct orderProduct) {
        orderProduct.setIdLigne(id);
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public void deleteLigneCommande(Long id) {
        orderProductRepository.deleteById(id);
    }

    @Override
    public OrderProduct getLigneCommandeById(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderProduct> getAllLigneCommandes() {
        return orderProductRepository.findAll();
    }*/


}
package o.springback.services.GestionCommande;

import jakarta.transaction.Transactional;
import o.springback.Interfaces.GestionCommande.OrderService;
import o.springback.entities.GestionCommande.Order;
import o.springback.repositories.Gestioncommande.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepo orderRepo;
    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepo.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        return this.orderRepo.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepo.save(order);
    }

    @Override
    public void deleteCommande(Long id) {
        orderRepo.deleteById(id);
    }

    /*
    public void updateStatus(Long idCommande, StatusCommande newStatus) {
        Order cmd = orderRepo.findById(idCommande).orElseThrow();
        cmd.setStatus(newStatus);
        // Envoyer notification si nécessaire
    }

    @Override
    public Order updateCommande(Long id, Order order) {
        order.setIdCommande(id);
        return orderRepo.save(order);
    }


    @Override
    public Order getCommandeById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAllCommandes() {
        return orderRepo.findAll();
    }*/
}
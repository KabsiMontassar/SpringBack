package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
 public List<Order> getUserOrders();

   List<Order> getAllOrders();
    Order create(Order order);
    public void update(Order order);
    void deleteCommande(Long id);

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(String status);

}

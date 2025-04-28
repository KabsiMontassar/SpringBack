package o.springback.Interfaces.GestionCommande;

import o.springback.entities.GestionCommande.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
   /* Order createCommande(Order order);
    Order creerCommande(Long clientId, Map<Long, Integer> produitsQuantites);
    Order updateCommande(Long id, Order order);
    Order getCommandeById(Long id);
    List<Order> getAllCommandes();



    //Commande annulerCommande(Commande commande);
    //List<Commande> getCommandesByClientId(Long clientId);
    //double calculTotal(List<Commande> items);
    //boolean validerCommande(List<Commande> items);
    */
   List<Order> getAllOrders();
    Order create(Order order);
    public void update(Order order);
    void deleteCommande(Long id);

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(String status);

}

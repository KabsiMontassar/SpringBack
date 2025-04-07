package o.springback.Interfaces.GestionCommande;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import o.springback.entities.GestionCommande.OrderProduct;

import java.util.List;

public interface OrderProductService {
    /*OrderProduct createLigneCommande(OrderProduct orderProduct);
    OrderProduct updateLigneCommande(Long id, OrderProduct orderProduct);
    void deleteLigneCommande(Long id);
    OrderProduct getLigneCommandeById(Long id);
    List<OrderProduct> getAllLigneCommandes();*/

    // LigneCommande modifierQte(Long id, int qte);

    OrderProduct create(@NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct);
}
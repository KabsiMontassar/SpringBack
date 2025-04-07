package o.springback.entities.GestionCommande;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionProduits.Products;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class OrderProduct {

    @EmbeddedId
    @JsonIgnore
    private OrderProductPK pk;

    @Column(nullable = false)
    private Integer quantity;

    // default constructor

    public OrderProduct(Order order, Products products, Integer quantity) {
        pk = new OrderProductPK();
        pk.setOrder(order);
        pk.setProducts(products);
        this.quantity = quantity;
    }

    @Transient
    public Products getProduct() {
        return this.pk.getProducts();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrix() * getQuantity();
    }


}
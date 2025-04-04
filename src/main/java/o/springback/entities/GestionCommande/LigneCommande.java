package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionProduits.Products;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idLigne;

    @ManyToOne
    Commande commande;

    @ManyToOne
    Products product;

    int quantite;

    public LigneCommande(Products p, Integer qte) {
        this.product = p;
        this.quantite = qte;
    }
}
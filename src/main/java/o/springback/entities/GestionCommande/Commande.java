package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionProduits.Products;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCommande;

    LocalDate dateCommande;

    @ManyToMany(mappedBy = "commandes")
    private Set<Products> produits;

}

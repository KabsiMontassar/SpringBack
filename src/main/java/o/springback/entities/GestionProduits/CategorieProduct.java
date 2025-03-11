package o.springback.entities.GestionProduits;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorieProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;
    private String nomCategorie;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private Set<Products> produits;
}


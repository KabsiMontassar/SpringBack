package o.springback.entities.GestionProduits;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class Products {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idProduit;
private String nom;
private String description;
private Long prix;
private Long quantiteDisponible;
private LocalDate dateAjout;
private String imageURL;
private String status;



    @ManyToOne
    Stock StockProducts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="ProductsAvisProducts")
    private Set<AvisProduct> ProductAvisProduct;

}

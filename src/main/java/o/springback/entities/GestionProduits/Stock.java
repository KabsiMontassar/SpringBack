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

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;
    private Long quantite;
    private LocalDate dateMaj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="StockProducts")
    private Set<Products> StockProducts;


}

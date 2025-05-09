package o.springback.entities.GestionProduits;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "stock")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class Stock{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;
    private String nom;
    private Long quantite;
    private LocalDate dateMaj = LocalDate.now();

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private Set<Products> produits;
}
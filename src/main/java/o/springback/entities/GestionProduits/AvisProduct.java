package o.springback.entities.GestionProduits;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "avis_product")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AvisProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvis;
    private String comment;
    private LocalDate dateAvis;

    @ManyToOne
    @JsonIgnoreProperties
    @JoinColumn(name = "idProduit")
    private Products produit;
}

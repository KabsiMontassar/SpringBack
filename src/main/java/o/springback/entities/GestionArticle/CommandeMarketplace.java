package o.springback.entities.GestionArticle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeMarketplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;
    private LocalDate dateCommande;
    private Float montantTotal;
    private String statutCommande;
    private String modePaiement;

    @ManyToOne
    @JoinColumn(name = "id_panier")
    private PanierArticle panierArticle;

}

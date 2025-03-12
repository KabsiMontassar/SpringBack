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
public class PanierArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanier;
    private Integer quantite;
    private LocalDate dateAjoutPanier;
    private Float prixUnitaire;
    private TypeArticle typeArticle;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToMany(mappedBy = "panierArticle", cascade = CascadeType.ALL)
    private List<CommandeMarketplace> commandeMarketplaces;
}

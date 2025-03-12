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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;
    private String titre;
    private String description;
    private Float prix;
    private String imageUrl;
    private LocalDate dateAjout;
    private String statut;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="article")
    private List<AvisArticle> avisArticles;

    @OneToMany(mappedBy="article", cascade = CascadeType.ALL)
    private List<PanierArticle> panierArticles;


}

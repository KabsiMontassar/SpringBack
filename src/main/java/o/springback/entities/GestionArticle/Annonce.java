package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAnnonce")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnnonce;
    private String titre;
    private String description;
    private Float prix;
    private TypeArticle categorie;
    private String localisation;
    private LocalDate datePublication;
    private String statutAnnonce;
    private String imagesUrl;

    @OneToMany(mappedBy = "annonce")
    private List<Commentaire> commentaires;
}

package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCommentaire")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommentaire;
    private String contenu;
    private LocalDate dateCommentaire;

    @ManyToOne
    @JoinColumn(name = "id_annonce")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Annonce annonce;
}

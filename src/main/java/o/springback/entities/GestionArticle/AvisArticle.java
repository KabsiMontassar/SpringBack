package o.springback.entities.GestionArticle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvis;
    private Integer note; // 1-5
    private String commentaire;
    private LocalDate dateAvis;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;
}

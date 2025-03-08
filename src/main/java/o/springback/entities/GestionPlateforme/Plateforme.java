package o.springback.entities.GestionPlateforme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Plateforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlateforme;

    private TypePack typePack;

    private String Couleur;

    private String nomPlateforme;

    private LocalDate dateCreation;

    private String Valabilite;

    private String description;

    private String logo;




}
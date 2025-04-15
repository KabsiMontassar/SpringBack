package o.springback.dto.GestionFormation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsFormationDTO {
    private Integer idDetaille;         // ✅ utile pour update/delete
    private String objectif;
    private String contenu;
    private int duree;
    private String materielRequis;
    private int idFormation;            // ✅ relation avec la formation

    // ✅ Constructeur simplifié utilisé dans FormationWithDetailDTO

}

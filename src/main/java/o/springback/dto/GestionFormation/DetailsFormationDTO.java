package o.springback.dto.GestionFormation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsFormationDTO {
    private Integer idDetaille;
    private String objectif;
    private String contenu;
    private int duree;
    private String materielRequis;
    private int idFormation;
}

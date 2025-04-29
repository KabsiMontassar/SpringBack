package o.springback.dto.GestionFormation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormationShortDTO {
    private int idFormation;
    private String nom;
    private String lieu;
    private String typeFormation;
}
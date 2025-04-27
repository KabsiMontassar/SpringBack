package o.springback.dto.GestionCommande;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LivraisonDTO {
    private String adresse;
    private String description;
    private double latitude;
    private double longitude;
    private Long commandeId;
}
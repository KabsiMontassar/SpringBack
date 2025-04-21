package o.springback.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivreurDTO extends UserDTO{
    private String vehicule;
    private String zoneLivraison;
    private String statutLivreur;
    //private String historiqueLivraison;
    //private String historiqueEvaluation;
    //private String historiqueReclamation;
    //private String historiqueAvis;
}

package o.springback.dto.GestionFormation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationResponseDTO {
    private int idParticipation;
    private String dateInscription;
    private boolean enAttente;
    private boolean certificatDelivre;
    private float noteFinale;
    private int waitingPosition;

    private FormationShortDTO formation;
}

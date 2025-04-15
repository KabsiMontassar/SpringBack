package o.springback.dto.GestionFormation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationRequestDto {
    private Long userId;
    private int formationId;
}

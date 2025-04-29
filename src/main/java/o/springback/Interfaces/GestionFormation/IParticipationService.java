package o.springback.Interfaces.GestionFormation;

import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.dto.GestionFormation.ParticipationResponseDTO;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionFormation.ParticipationStatus;

import java.util.List;

public interface IParticipationService {

    Participation addParticipation(Participation participation);
    Participation updateParticipation(int id, Participation participation);
    void deleteParticipation(int id);
    Participation getParticipationById(int id);
    List<Participation> getAllParticipations();

    Participation participate(ParticipationRequestDto dto);
    void annulerParticipation(int id);
    Participation enregistrerNoteEtEvaluerCertificat(int idParticipation, float note);
    List<ParticipationResponseDTO> getMyParticipationDTOs();
    int getWaitingPosition(int formationId);
    long countConfirmedParticipants(int formationId);
    List<Participation> getAllWaitingForFormation(int formationId);
    Formation getConflictingFormation(Formation newFormation);
    boolean isUserBlockedForFormation(int formationId);
    long getRemainingBlockTimeForUser(int formationId);
    boolean isUserAlreadyParticipating(int formationId);
    Formation checkConflict(int formationId);
}

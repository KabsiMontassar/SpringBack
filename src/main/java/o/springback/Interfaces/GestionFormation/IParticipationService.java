package o.springback.Interfaces.GestionFormation;

import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.entities.GestionFormation.Participation;

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
}

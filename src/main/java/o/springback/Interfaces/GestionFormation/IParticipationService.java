package o.springback.Interfaces.GestionFormation;

import o.springback.entities.GestionFormation.Participation;

import java.util.List;

public interface IParticipationService {
    Participation addParticipation(Participation participation);
    Participation updateParticipation(int id, Participation participation);
    void deleteParticipation(int id);
    Participation getParticipationById(int id);
    List<Participation> getAllParticipations();
}

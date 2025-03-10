package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.entities.GestionFormation.Participation;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationService implements IParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    public Participation addParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    @Override
    public Participation updateParticipation(int id, Participation participation) {
        participation.setIdParticipation(id); // Assurez-vous que l'ID est correct
        return participationRepository.save(participation);
    }

    @Override
    public void deleteParticipation(int id) {
        participationRepository.deleteById(id);
    }

    @Override
    public Participation getParticipationById(int id) {
        return participationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }
}

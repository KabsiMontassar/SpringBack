package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParticipationService implements IParticipationService {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private FormationRepository FormationRepository;


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

    public Participation participate(ParticipationRequestDto dto) {
        User user = UserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Formation formation = FormationRepository.findById(dto.getFormationId())
                .orElseThrow(() -> new RuntimeException("Formation not found"));

        // Vérification du rôle
        if (!user.getRole().equalsIgnoreCase("AGRICULTEUR")) {
            throw new RuntimeException("Seuls les agriculteurs peuvent participer");
        }

        // Vérifie s’il a déjà participé
        if (participationRepository.existsByUserAndFormation(user, formation)) {
            throw new RuntimeException("Vous êtes déjà inscrit à cette formation");
        }

        // Vérification de la capacité de la formation
        int capacity = formation.getCapacity(); // Capacité maximale de la formation
        long currentParticipants = participationRepository.countByFormation(formation); // Nombre actuel de participants

        if (currentParticipants >= capacity) {
            // Si la formation est pleine, l'utilisateur sera ajouté à la liste d'attente
            Participation participation = new Participation();
            participation.setUser(user);
            participation.setFormation(formation);
            participation.setDateInscription(new Date());
            participation.setCertificatDelivre(false);
            participation.setEnAttente(true); // Ajout de l'état "en attente"
            return participationRepository.save(participation);
        }

        // Si la formation n'est pas pleine, l'utilisateur est inscrit normalement
        Participation participation = new Participation();
        participation.setUser(user);
        participation.setFormation(formation);
        participation.setDateInscription(new Date());
        participation.setCertificatDelivre(false);

        return participationRepository.save(participation);
    }


    public void annulerParticipation(int participationId) {
        Participation p = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        long diff = new Date().getTime() - p.getDateInscription().getTime();
        long hours = diff / (1000 * 60 * 60);

        if (hours > 24) {
            throw new RuntimeException("Impossible d'annuler après 24h");
        }

        // Si l'utilisateur annule sa participation, il doit être supprimé de la liste et remplacé par un utilisateur en attente
        participationRepository.deleteById(participationId);

        // Recherche des utilisateurs en attente
        List<Participation> waitingList = participationRepository.findByFormationAndEnAttente(p.getFormation(), true);

        if (!waitingList.isEmpty()) {
            // Remplacer le premier utilisateur en attente
            Participation waitingUser = waitingList.get(0);
            waitingUser.setEnAttente(false); // L'utilisateur n'est plus en attente
            participationRepository.save(waitingUser);
        }
    }
    public Participation enregistrerNoteEtEvaluerCertificat(int participationId, float note) {
        Participation p = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation non trouvée"));

        p.setNoteFinale(note);

        Formation f = p.getFormation();
        if (f.isCertification() && note >= f.getNoteMinPourCertificat()) {
            p.setCertificatDelivre(true);
        } else {
            p.setCertificatDelivre(false);
        }

        return participationRepository.save(p);
    }


}

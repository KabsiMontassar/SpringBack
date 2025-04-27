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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParticipationService implements IParticipationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public Participation addParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    @Override
    public Participation updateParticipation(int id, Participation participation) {
        participation.setIdParticipation(id);
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

    @Override
    public Participation participate(ParticipationRequestDto dto) {
        User user = getCurrentUser();
        Formation formation = formationRepository.findById(dto.getFormationId())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        if (participationRepository.existsByUserAndFormation(user, formation)) {
            throw new RuntimeException("Vous êtes déjà inscrit à cette formation");
        }

        int capacity = formation.getCapacity();
        long currentParticipants = participationRepository.countConfirmedParticipantsByFormationId(formation.getIdFormation());

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setFormation(formation);
        participation.setDateInscription(new Date());
        participation.setCertificatDelivre(false);

        if (currentParticipants >= capacity) {
            participation.setEnAttente(true);
        } else {
            participation.setEnAttente(false);
        }

        participation = participationRepository.save(participation);

        // 🔥 RE-CHARGER depuis DB après Save pour être sûr
        List<Participation> waitingList = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);

        if (participation.isEnAttente()) {
            int position = -1;
            for (int i = 0; i < waitingList.size(); i++) {
                if (waitingList.get(i).getUser().getIdUser().equals(user.getIdUser())) {
                    position = i + 1;
                    break;
                }
            }
            participation.setWaitingPosition(position);
        } else {
            participation.setWaitingPosition(0); // Pas en attente
        }

        return participation;
    }

    @Override
    public void annulerParticipation(int participationId) {
        Participation p = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation introuvable"));

        long diff = new Date().getTime() - p.getDateInscription().getTime();
        long hours = diff / (1000 * 60 * 60);

        if (hours > 24) {
            throw new RuntimeException("Impossible d'annuler après 24h");
        }

        Formation formation = p.getFormation(); // 📦 Sauvegarder la formation avant delete

        participationRepository.deleteById(participationId);

        // Après annulation, vérifier la capacité restante
        long confirmedParticipants = participationRepository.countConfirmedParticipantsByFormationId(formation.getIdFormation());

        if (confirmedParticipants < formation.getCapacity()) {
            List<Participation> waitingList = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);
            if (!waitingList.isEmpty()) {
                Participation nextInLine = waitingList.get(0);
                nextInLine.setEnAttente(false);
                participationRepository.save(nextInLine);
            }
        }
    }

    @Override
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

    @Override
    public List<Participation> getMyParticipations() {
        User user = getCurrentUser();
        return participationRepository.findByUser(user);
    }

    @Override
    public int getWaitingPosition(int formationId) {
        User user = getCurrentUser();
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        List<Participation> waitingList = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);

        for (int i = 0; i < waitingList.size(); i++) {
            if (waitingList.get(i).getUser().getIdUser().equals(user.getIdUser())) {
                return i + 1;
            }
        }

        return 0; // Pas inscrit
    }

    @Override
    public long countConfirmedParticipants(int formationId) {
        return participationRepository.countConfirmedParticipantsByFormationId(formationId);
    }
    @Override
    public List<Participation> getAllWaitingForFormation(int formationId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);
    }


}

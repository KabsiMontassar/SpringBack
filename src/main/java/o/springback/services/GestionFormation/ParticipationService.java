package o.springback.services.GestionFormation;

import jakarta.transaction.Transactional;
import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionFormation.ParticipationStatus;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import o.springback.repositories.GestionFormation.ParticipationStatusRepository;
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
    @Autowired
    private ParticipationStatusRepository participationStatusRepository;

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

    public User getCurrentConnectedUser() {
        return getCurrentUser();
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
    @Transactional
    public Participation participate(ParticipationRequestDto dto) {
        User user = getCurrentUser();
        Formation formation = formationRepository.findById(dto.getFormationId())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        ParticipationStatus status = participationStatusRepository.findByUserAndFormation(user, formation).orElse(null);

        if (status != null && status.isBloque()) {
            if (status.getDateBlocage() != null) {
                int secondsPenalty = status.getNombreAnnulations() * 10;
                long elapsedMillis = new Date().getTime() - status.getDateBlocage().getTime();
                if (elapsedMillis >= secondsPenalty * 1000L) {
                    // ✅ Déblocage terminé : update en mémoire ET SAVE
                    status.setBloque(false);
                    status.setNombreAnnulations(0);
                    participationStatusRepository.save(status);

                    // ✅ BESTIE : on met à jour l'objet status pour rechecker après
                    status = participationStatusRepository.findByUserAndFormation(user, formation)
                            .orElse(null);
                }
            }
        }

// 🚨 ici on RECHECK AVEC L'OBJET RÉEL DE LA BASE !
        if (status != null && status.isBloque()) {
            throw new RuntimeException("Vous êtes bloqué pour cette formation. 😔");
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
            participation.setWaitingPosition(0);
        }

        return participation;
    }



    @Override
    @Transactional
    public void annulerParticipation(int participationId) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation introuvable"));

        Formation formation = participation.getFormation();
        User user = participation.getUser();
        Date dateDebutFormation = formation.getDateDebut();
        Date now = new Date();

        long diffMillis = dateDebutFormation.getTime() - now.getTime();
        long diffHours = diffMillis / (1000 * 60 * 60);

        if (diffHours <= 24) {
            throw new RuntimeException("Impossible d'annuler : il reste moins de 24h avant le début de la formation.");
        }

        // ➡️ Récupérer ou créer ParticipationStatus
        ParticipationStatus status = participationStatusRepository.findByUserAndFormation(user, formation)
                .orElseGet(() -> {
                    ParticipationStatus newStatus = new ParticipationStatus();
                    newStatus.setUser(user);
                    newStatus.setFormation(formation);
                    return newStatus;
                });

        // ➡️ Incrémenter les annulations
        status.setNombreAnnulations(status.getNombreAnnulations() + 1);

        // ➡️ Bloquer si nécessaire
        if (status.getNombreAnnulations() >= 3) {
            status.setBloque(true);
            status.setDateBlocage(new Date());
        }

        participationStatusRepository.save(status);

        // ➡️ ⚡ SUPPRIMER la participation (toujours)
        participationRepository.delete(participation);

        // ➡️ ⚡ Rééquilibrer la liste d'attente uniquement si pas bloqué
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




    @Override
    public Formation getConflictingFormation(User user, Formation newFormation) {
        List<Participation> participations = participationRepository.findByUser(user);

        for (Participation p : participations) {
            Formation f = p.getFormation();
            if (f != null) {
                boolean overlap = !(newFormation.getDateFin().before(f.getDateDebut()) || newFormation.getDateDebut().after(f.getDateFin()));
                if (overlap) {
                    return f; // Retourner la formation conflictuelle
                }
            }
        }
        return null;
    }

    public ParticipationStatus getParticipationStatus(User user, Formation formation) {
        return participationStatusRepository.findByUserAndFormation(user, formation).orElse(null);
    }

    public boolean isUserBlockedForFormation(int formationId) {
        User user = getCurrentConnectedUser();
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        ParticipationStatus status = getParticipationStatus(user, formation);
        return status != null && status.isBloque();
    }

    public long getRemainingBlockTime(User user, Formation formation) {
        ParticipationStatus status = participationStatusRepository.findByUserAndFormation(user, formation)
                .orElse(null);
        if (status == null || !status.isBloque() || status.getDateBlocage() == null) {
            return 0; // Pas bloqué
        }

        int secondsPenalty = status.getNombreAnnulations() * 10; // 10 secondes x nombre d'annulations

        long elapsedMillis = new Date().getTime() - status.getDateBlocage().getTime();
        long remainingMillis = secondsPenalty * 1000 - elapsedMillis;

        return Math.max(remainingMillis / 1000, 0); // Retourner les secondes restantes
    }

    @Override
    public boolean isUserAlreadyParticipating(User user, Formation formation) {
        return participationRepository.existsByUserAndFormation(user, formation);
    }

}

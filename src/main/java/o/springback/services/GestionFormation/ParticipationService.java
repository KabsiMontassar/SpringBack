package o.springback.services.GestionFormation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.FormationShortDTO;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.dto.GestionFormation.ParticipationResponseDTO;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionFormation.ParticipationStatus;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import o.springback.repositories.GestionFormation.ParticipationStatusRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipationService implements IParticipationService {

    private UserRepository userRepository;
    private FormationRepository formationRepository;
    private ParticipationRepository participationRepository;
    private ParticipationStatusRepository participationStatusRepository;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
        return userRepository.findByEmail(email).orElse(null);
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
        Formation formation = formationRepository.findById(dto.getFormationId()).orElse(null);
        if (user == null || formation == null) return null;

        ParticipationStatus status = participationStatusRepository.findByUserAndFormation(user, formation).orElse(null);
        if (status != null && status.isBloque() && status.getDateBlocage() != null) {
            long elapsedMillis = new Date().getTime() - status.getDateBlocage().getTime();
            int secondsPenalty = status.getNombreAnnulations() * 10;
            if (elapsedMillis >= secondsPenalty * 1000L) {
                status.setBloque(false);
                status.setNombreAnnulations(0);
                participationStatusRepository.save(status);
            }
        }

        if (status != null && status.isBloque()) return null;

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setFormation(formation);
        participation.setDateInscription(new Date());
        participation.setCertificatDelivre(false);

        long confirmed = participationRepository.countConfirmedParticipantsByFormationId(formation.getIdFormation());
        participation.setEnAttente(confirmed >= formation.getCapacity());

        participation = participationRepository.save(participation);

        if (participation.isEnAttente()) {
            List<Participation> waitingList = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);
            for (int i = 0; i < waitingList.size(); i++) {
                if (waitingList.get(i).getUser().getIdUser().equals(user.getIdUser())) {
                    participation.setWaitingPosition(i + 1);
                    break;
                }
            }
        } else {
            participation.setWaitingPosition(0);
        }

        return participation;
    }

    @Override
    public void annulerParticipation(int id) {
        Participation participation = participationRepository.findById(id).orElse(null);
        if (participation == null) return;

        Formation formation = participation.getFormation();
        User user = participation.getUser();

        long diff = formation.getDateDebut().getTime() - new Date().getTime();
        long hours = diff / (1000 * 60 * 60);
        if (hours <= 24) return;

        ParticipationStatus status = participationStatusRepository.findByUserAndFormation(user, formation)
                .orElseGet(() -> {
                    ParticipationStatus s = new ParticipationStatus();
                    s.setUser(user);
                    s.setFormation(formation);
                    return s;
                });

        status.setNombreAnnulations(status.getNombreAnnulations() + 1);
        if (status.getNombreAnnulations() >= 3) {
            status.setBloque(true);
            status.setDateBlocage(new Date());
        }

        participationStatusRepository.save(status);
        participationRepository.delete(participation);

        long confirmed = participationRepository.countConfirmedParticipantsByFormationId(formation.getIdFormation());
        if (confirmed < formation.getCapacity()) {
            List<Participation> waitingList = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(formation, true);
            if (!waitingList.isEmpty()) {
                Participation next = waitingList.get(0);
                next.setEnAttente(false);
                participationRepository.save(next);
            }
        }
    }

    @Override
    public Participation enregistrerNoteEtEvaluerCertificat(int id, float note) {
        Participation p = participationRepository.findById(id).orElse(null);
        if (p == null) return null;

        p.setNoteFinale(note);
        if (p.getFormation().isCertification() && note >= p.getFormation().getNoteMinPourCertificat()) {
            p.setCertificatDelivre(true);
        } else {
            p.setCertificatDelivre(false);
        }
        return participationRepository.save(p);
    }

    @Override
    public List<Participation> getAllWaitingForFormation(int id) {
        Formation f = formationRepository.findById(id).orElse(null);
        return (f != null) ? participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(f, true) : List.of();
    }

    @Override
    public int getWaitingPosition(int formationId) {
        User user = getCurrentUser();
        Formation f = formationRepository.findById(formationId).orElse(null);
        if (f == null || user == null) return 0;

        List<Participation> list = participationRepository.findByFormationAndEnAttenteOrderByDateInscriptionAsc(f, true);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser().getIdUser().equals(user.getIdUser())) return i + 1;
        }
        return 0;
    }

    @Override
    public long countConfirmedParticipants(int formationId) {
        return participationRepository.countConfirmedParticipantsByFormationId(formationId);
    }

    @Override
    public boolean isUserBlockedForFormation(int id) {
        User user = getCurrentUser();
        Formation f = formationRepository.findById(id).orElse(null);
        ParticipationStatus s = (user != null && f != null) ? participationStatusRepository.findByUserAndFormation(user, f).orElse(null) : null;
        return s != null && s.isBloque();
    }

    @Override
    public long getRemainingBlockTimeForUser(int formationId) {
        User user = getCurrentUser();
        Formation f = formationRepository.findById(formationId).orElse(null);
        if (user == null || f == null) return 0;

        ParticipationStatus s = participationStatusRepository.findByUserAndFormation(user, f).orElse(null);
        if (s == null || !s.isBloque()) return 0;

        long elapsed = new Date().getTime() - s.getDateBlocage().getTime();
        long remaining = s.getNombreAnnulations() * 10 * 1000 - elapsed;
        return Math.max(remaining / 1000, 0);
    }

    @Override
    public boolean isUserAlreadyParticipating(int formationId) {
        User user = getCurrentUser();
        Formation f = formationRepository.findById(formationId).orElse(null);
        return user != null && f != null && participationRepository.existsByUserAndFormation(user, f);
    }

    @Override
    public Formation checkConflict(int formationId) {
        User user = getCurrentUser();
        Formation newFormation = formationRepository.findById(formationId).orElse(null);
        return (user != null && newFormation != null) ? getConflictingFormation(newFormation) : null;
    }

    @Override
    public Formation getConflictingFormation(Formation newFormation) {
        User user = getCurrentUser();
        if (user == null) return null;

        List<Participation> participations = participationRepository.findByUser(user);
        for (Participation p : participations) {
            Formation f = p.getFormation();
            if (f != null && !(newFormation.getDateFin().before(f.getDateDebut()) || newFormation.getDateDebut().after(f.getDateFin()))) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<ParticipationResponseDTO> getMyParticipationDTOs() {
        User user = getCurrentUser();
        List<Participation> list = participationRepository.findByUser(user);

        return list.stream().map(p -> {
            ParticipationResponseDTO dto = new ParticipationResponseDTO();
            dto.setIdParticipation(p.getIdParticipation());
            dto.setDateInscription(p.getDateInscription().toString());
            dto.setEnAttente(p.isEnAttente());
            dto.setCertificatDelivre(p.isCertificatDelivre());
            dto.setNoteFinale(p.getNoteFinale());
            dto.setWaitingPosition(p.isEnAttente() ? getWaitingPosition(p.getFormation().getIdFormation()) : 0);

            ParticipationStatus s = getParticipationStatus(user, p.getFormation());
            dto.setBloque(s != null && s.isBloque());

            if (p.getFormation() != null) {
                FormationShortDTO f = new FormationShortDTO();
                f.setIdFormation(p.getFormation().getIdFormation());
                f.setNom(p.getFormation().getNom());
                f.setLieu(p.getFormation().getLieu());
                f.setTypeFormation(p.getFormation().getTypeFormation().name());
                dto.setFormation(f);
            }
            return dto;
        }).toList();
    }

    public ParticipationStatus getParticipationStatus(User user, Formation formation) {
        return participationStatusRepository.findByUserAndFormation(user, formation).orElse(null);
    }
}

package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.FormationShortDTO;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.dto.GestionFormation.ParticipationResponseDTO;
import o.springback.entities.GestionFormation.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/participations")
@AllArgsConstructor


public class ParticipationController {


    private IParticipationService participationService;

    @PostMapping
    public Participation addParticipation(@RequestBody Participation participation) {
        return participationService.addParticipation(participation);
    }

    @PutMapping("/{id}")
    public Participation updateParticipation(@PathVariable int id, @RequestBody Participation participation) {
        return participationService.updateParticipation(id, participation);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipation(@PathVariable int id) {
        participationService.deleteParticipation(id);
    }

    @GetMapping("/{id}")
    public Participation getParticipationById(@PathVariable int id) {
        return participationService.getParticipationById(id);
    }

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    @PostMapping("/inscrire")
    public Participation participate(@RequestBody ParticipationRequestDto dto) {
        return participationService.participate(dto);
    }

    @DeleteMapping("/annuler/{id}")
    public void annulerParticipation(@PathVariable int id) {
        participationService.annulerParticipation(id);
    }

    @PostMapping("/noter/{idParticipation}")
    public Participation noterParticipant(@PathVariable int idParticipation, @RequestParam float note) {
        return participationService.enregistrerNoteEtEvaluerCertificat(idParticipation, note);
    }
    @GetMapping("/mes-participations")
    public List<ParticipationResponseDTO> getMyParticipations() {
        List<Participation> participations = participationService.getMyParticipations();

        return participations.stream().map(p -> {
            ParticipationResponseDTO dto = new ParticipationResponseDTO();
            dto.setIdParticipation(p.getIdParticipation());
            dto.setDateInscription(p.getDateInscription().toString());
            dto.setEnAttente(p.isEnAttente());
            dto.setCertificatDelivre(p.isCertificatDelivre());
            dto.setNoteFinale(p.getNoteFinale());

            if (p.isEnAttente()) {
                // 🔥🔥🔥 Nouveau calcul dynamique correct ici 🔥🔥🔥
                List<Participation> waitingList = participationService.getAllWaitingForFormation(p.getFormation().getIdFormation());
                for (int i = 0; i < waitingList.size(); i++) {
                    if (waitingList.get(i).getUser().getIdUser().equals(p.getUser().getIdUser())) {
                        dto.setWaitingPosition(i + 1);
                        break;
                    }
                }
            } else {
                dto.setWaitingPosition(0);
            }

            if (p.getFormation() != null) {
                FormationShortDTO formationDto = new FormationShortDTO();
                formationDto.setIdFormation(p.getFormation().getIdFormation());
                formationDto.setNom(p.getFormation().getNom());
                formationDto.setLieu(p.getFormation().getLieu());
                formationDto.setTypeFormation(p.getFormation().getTypeFormation().name());
                dto.setFormation(formationDto);
            }

            return dto;
        }).toList();
    }
    @GetMapping("/waiting-position/{formationId}")
    public int getWaitingPosition(@PathVariable int formationId) {
        return participationService.getWaitingPosition(formationId);
    }

    @GetMapping("/count-confirmed/{formationId}")
    public long countConfirmedParticipants(@PathVariable int formationId) {
        return participationService.countConfirmedParticipants(formationId);
    }

}

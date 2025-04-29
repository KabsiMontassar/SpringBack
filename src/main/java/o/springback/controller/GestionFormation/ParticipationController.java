package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.FormationShortDTO;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.dto.GestionFormation.ParticipationResponseDTO;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionUser.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> annulerParticipation(@PathVariable int id) {
        try {
            participationService.annulerParticipation(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("message", e.getReason())); // ✅ clé "message"
        }
    }

    @PostMapping("/noter/{idParticipation}")
    public Participation noterParticipant(@PathVariable int idParticipation, @RequestParam float note) {
        return participationService.enregistrerNoteEtEvaluerCertificat(idParticipation, note);
    }

    @GetMapping("/mes-participations")
    public List<ParticipationResponseDTO> getMyParticipations() {
        return participationService.getMyParticipationDTOs();
    }

    @GetMapping("/waiting-position/{formationId}")
    public int getWaitingPosition(@PathVariable int formationId) {
        return participationService.getWaitingPosition(formationId);
    }

    @GetMapping("/count-confirmed/{formationId}")
    public long countConfirmedParticipants(@PathVariable int formationId) {
        return participationService.countConfirmedParticipants(formationId);
    }

    @GetMapping("/check-conflict/{formationId}")
    public Formation checkConflict(@PathVariable int formationId) {
        return participationService.checkConflict(formationId);
    }

    @GetMapping("/check-blocked/{formationId}")
    public boolean checkUserBlocked(@PathVariable int formationId) {
        return participationService.isUserBlockedForFormation(formationId);
    }

    @GetMapping("/remaining-block-time/{formationId}")
    public long getRemainingBlockTime(@PathVariable int formationId) {
        return participationService.getRemainingBlockTimeForUser(formationId);
    }

    @GetMapping("/is-participating/{formationId}")
    public boolean isUserAlreadyParticipating(@PathVariable int formationId) {
        return participationService.isUserAlreadyParticipating(formationId);
    }
}

package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.dto.GestionFormation.ParticipationRequestDto;
import o.springback.entities.GestionFormation.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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


}

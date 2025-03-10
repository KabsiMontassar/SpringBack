package o.springback.controller.GestionFormation;

import o.springback.Interfaces.GestionFormation.IParticipationService;
import o.springback.entities.GestionFormation.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
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
}

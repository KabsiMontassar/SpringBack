package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.services.GestionFormation.StatistiqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AdminStatController {

    private final StatistiqueService statistiqueService;

   
    @GetMapping("/taux-reussite")
    public List<Object[]> tauxReussite() {
        return statistiqueService.getTauxReussiteParFormation();
    }

    @GetMapping("/plus-populaires")
    public List<Object[]> populaires() {
        return statistiqueService.getFormationsPopulaires();
    }

    @GetMapping("/moyenne-notes")
    public List<Object[]> moyenneNotes() {
        return statistiqueService.getMoyenneNotesParFormation();
    }

    @GetMapping("/total")
    public long totalFormations() {
        return statistiqueService.getTotalFormations();
    }

    @GetMapping("/certifiantes")
    public long totalFormationsCertifiantes() {
        return statistiqueService.getFormationsCertifiantes();
    }

    @GetMapping("/moyenne-capacite")
    public Double moyenneCapacite() {
        return statistiqueService.getMoyenneCapacite();
    }

    @GetMapping("/par-type")
    public List<Object[]> formationsParType() {
        return statistiqueService.getFormationsParType();
    }

    @GetMapping("/moyenne-duree")
    public Double moyenneDuree() {
        return statistiqueService.getMoyenneDureeDetails();
    }

    @GetMapping("/sans-details")
    public long formationsSansDetails() {
        return statistiqueService.getFormationsSansDetails();
    }
}

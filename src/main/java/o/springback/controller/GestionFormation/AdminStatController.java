package o.springback.controller.GestionFormation;

import o.springback.services.GestionFormation.StatistiqueService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class AdminStatController {
    private StatistiqueService statistiqueService;

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
}

package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.entities.GestionFormation.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
@AllArgsConstructor
public class FormationController {


    private IFormationService formationService;

    @PostMapping("/add")
    public Formation addFormation(@RequestBody Formation formation) {
        return formationService.addFormation(formation);
    }

    @PutMapping("/add/{id}")
    public Formation updateFormation(@PathVariable int id, @RequestBody Formation formation) {
        return formationService.updateFormation(id, formation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFormation(@PathVariable int id) {
        formationService.deleteFormation(id);
    }

    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable int id) {
        return formationService.getFormationById(id);
    }

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }
}
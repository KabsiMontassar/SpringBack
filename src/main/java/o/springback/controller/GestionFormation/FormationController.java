package o.springback.controller.GestionFormation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.entities.GestionFormation.Formation;
import o.springback.services.GestionFormation.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/formations")
@AllArgsConstructor
public class FormationController {

    private IFormationService formationService;


    @PostMapping("/add")
    public Formation addFormation(@RequestParam("formation") String formationJson,
                                  @RequestParam(value = "photo", required = false) MultipartFile photo) throws JsonProcessingException {
        Formation formation = new ObjectMapper().readValue(formationJson, Formation.class);
        return formationService.addFormation(formation, photo);
    }


    @PutMapping("/update/{id}")
    public Formation updateFormation(@PathVariable int id, @RequestParam("formation") String formationJson, @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Formation formation = new ObjectMapper().readValue(formationJson, Formation.class);
        return formationService.updateFormation(id, formation, photo);
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


    @Autowired
    private FormationService formationServicee;

    // Endpoint pour obtenir le taux de réussite d'une formation
    @GetMapping("/formations/{id}/taux-reussite")
    public Object[] obtenirTauxReussite(@PathVariable("id") int formationId) {
        return formationService.obtenirTauxReussite(formationId);
    }
}

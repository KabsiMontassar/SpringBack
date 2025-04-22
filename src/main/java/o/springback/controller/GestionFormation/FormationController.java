package o.springback.controller.GestionFormation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.dto.GestionFormation.FormationWithDetailDTO;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.services.GestionFormation.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "true")
@RequestMapping("/formations")
@AllArgsConstructor
public class FormationController {
/*
    private IFormationService formationService;

    @Autowired
    private FormationRepository FormationRepository;


    @PostMapping("/add")
    public Formation addFormation(@RequestParam("formation") String formationJson,
                                  @RequestParam(value = "photo", required = false) MultipartFile photo) throws JsonProcessingException {
        Formation formation = new ObjectMapper().readValue(formationJson, Formation.class);
        return formationService.addFormation(formation, photo);
    }


    @PutMapping("/update/{id}")
    public Formation updateFormation(
            @PathVariable int id,
            @RequestParam("formation") String formationJson,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws JsonProcessingException {
        Formation formation = new ObjectMapper().readValue(formationJson, Formation.class);
        return formationService.updateFormation(id, formation, photo);
    }



    @DeleteMapping("/delete/{id}")
    public void deleteFormation(@PathVariable int id) {
        formationService.deleteFormation(id);
    }


    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable int id) {
        Formation formation = formationService.getFormationById(id);
        if (formation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Formation not found");
        }
        return formation;
    }


    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }


    @Autowired
    private FormationService formationServicee;


    @GetMapping("/{id}/taux-reussite")
    public Object[] obtenirTauxReussite(@PathVariable("id") int formationId) {
        return formationService.obtenirTauxReussite(formationId);
    }

    @GetMapping("/calendar")
    public List<Map<String, Object>> getFormationsForCalendar() {
        List<Formation> formations = FormationRepository.findAll();
        List<Map<String, Object>> events = new ArrayList<>();

        for (Formation f : formations) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", f.getNom());
            event.put("start", f.getDateDebut());
            event.put("end", f.getDateFin());
            event.put("id", f.getIdFormation());
            events.add(event);
        }

        return events;
    }

    @GetMapping("/with-details/{id}")
    public FormationWithDetailDTO getFormationWithDetailsById(@PathVariable int id) {
        Formation formation = formationService.getFormationById(id);
        if (formation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Formation not found");
        }
        return new FormationWithDetailDTO(formation);
    }
    @GetMapping("/all-with-details")
    public List<FormationWithDetailDTO> getAllFormationsWithDetails() {
        return FormationRepository.findAll().stream()
                .map(FormationWithDetailDTO::new)
                .collect(Collectors.toList());
    }
*/
}


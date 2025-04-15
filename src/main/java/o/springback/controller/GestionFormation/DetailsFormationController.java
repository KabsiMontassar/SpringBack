package o.springback.controller.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionFormation.IDetailsFormationService;
import o.springback.dto.GestionFormation.DetailsFormationDTO;
import o.springback.entities.GestionFormation.DetailsFormation;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.FormationRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "true")
@RequestMapping("/details-formation")
@AllArgsConstructor
public class DetailsFormationController {

    private IDetailsFormationService detailFormationService;
    private FormationRepository formationRepository;

    // ✅ Ajouter avec DTO
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DetailsFormation addDetailFormation(@RequestBody DetailsFormationDTO dto) {
        Formation formation = formationRepository.findById(dto.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'ID: " + dto.getIdFormation()));

        DetailsFormation detail = new DetailsFormation();
        detail.setObjectif(dto.getObjectif());
        detail.setContenu(dto.getContenu());
        detail.setDuree(dto.getDuree());
        detail.setMaterielRequis(dto.getMaterielRequis());
        detail.setFormation(formation);

        return detailFormationService.addDetailFormation(detail);
    }

    // ✅ Update avec DTO
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DetailsFormation updateDetailFormation(@PathVariable int id, @RequestBody DetailsFormationDTO dto) {
        Formation formation = formationRepository.findById(dto.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'ID: " + dto.getIdFormation()));

        DetailsFormation detail = new DetailsFormation();
        detail.setIdDetaille(id);
        detail.setObjectif(dto.getObjectif());
        detail.setContenu(dto.getContenu());
        detail.setDuree(dto.getDuree());
        detail.setMaterielRequis(dto.getMaterielRequis());
        detail.setFormation(formation);

        return detailFormationService.updateDetailFormation(id, detail);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDetailFormation(@PathVariable int id) {
        detailFormationService.deleteDetailFormation(id);
    }

    @GetMapping("/{id}")
    public DetailsFormationDTO getDetailFormationById(@PathVariable int id) {
        DetailsFormation detail = detailFormationService.getDetailFormationById(id);

        if (detail == null) {
            return null;
        }

        return new DetailsFormationDTO(
                detail.getIdDetaille(),
                detail.getObjectif(),
                detail.getContenu(),
                detail.getDuree(),
                detail.getMaterielRequis(),
                detail.getFormation().getIdFormation() // ✅ important
        );
    }


}

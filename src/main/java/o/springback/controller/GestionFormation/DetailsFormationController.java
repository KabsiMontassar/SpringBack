package o.springback.controller.GestionFormation;

import o.springback.Interfaces.GestionFormation.IDetailsFormationService;
import o.springback.entities.GestionFormation.DetailsFormation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/details-formation")
public class DetailsFormationController {

    @Autowired
    private IDetailsFormationService detailFormationService;

    @PostMapping
    public DetailsFormation addDetailFormation(@RequestBody DetailsFormation detailFormation) {
        return detailFormationService.addDetailFormation(detailFormation);
    }

    @PutMapping("/{id}")
    public DetailsFormation updateDetailFormation(@PathVariable int id, @RequestBody DetailsFormation detailFormation) {
        return detailFormationService.updateDetailFormation(id, detailFormation);
    }

    @DeleteMapping("/{id}")
    public void deleteDetailFormation(@PathVariable int id) {
        detailFormationService.deleteDetailFormation(id);
    }

    @GetMapping("/{id}")
    public DetailsFormation getDetailFormationById(@PathVariable int id) {
        return detailFormationService.getDetailFormationById(id);
    }
}


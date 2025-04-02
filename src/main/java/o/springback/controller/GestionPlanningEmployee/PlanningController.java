package o.springback.controller.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.IPlanningService;
import o.springback.entities.GestionPlanningEmployee.Planning;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/planning")
public class PlanningController {

    IPlanningService planningService;

    @GetMapping("/retrieve-all-Plannings")
    public List<Planning> getPlannings() {
        return planningService.findAll();
    }

    @GetMapping("/retrieve-Planning/{Planning-id}")
    public Planning retrievePlanning(@PathVariable("Planning-id") Long PlanningId) {
        return planningService.findById(PlanningId);
    }
    @PostMapping("/add-Planning")
    public Planning addPlanning(@RequestBody Planning c) {
        return planningService.save(c);
    }

    @DeleteMapping("/remove-Planning/{Planning-id}")
    public void removePlanning(@PathVariable("Planning-id") Long PlanningId) {
        planningService.delete(PlanningId);
    }
    @PutMapping("/update-Planning")
    public Planning updatePlanning(@RequestBody Planning c) {
        return planningService.update(c);
    }
}
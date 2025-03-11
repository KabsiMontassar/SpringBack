package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.entities.GestionPlateforme.Plateforme;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/plateforme")
public class PlateformeController {

    IPlateformeService plateformeService;

    @GetMapping("/retrieve-all-Plateformes")
    public List<Plateforme> getPlateformes() {
        return plateformeService.findAll();
    }

    @GetMapping("/retrieve-Plateforme/{Plateforme-id}")
    public Plateforme retrievePlateforme(@PathVariable("Plateforme-id") Long PlateformeId) {
        return plateformeService.findById(PlateformeId);
    }
    @PostMapping("/add-Plateforme")
    public Plateforme addPlateforme(@RequestBody Plateforme c) {
        return plateformeService.save(c);
    }

    @DeleteMapping("/remove-Plateforme/{Plateforme-id}")
    public void removePlateforme(@PathVariable("Plateforme-id") Long PlateformeId) {
        plateformeService.delete(PlateformeId);
    }
    @PutMapping("/update-Plateforme")
    public Plateforme updatePlateforme(@RequestBody Plateforme c) {
        return plateformeService.update(c);
    }
}

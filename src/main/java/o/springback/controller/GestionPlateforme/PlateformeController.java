package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionPlateforme.Plateforme;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/plateforme")
public class PlateformeController {

    IPlateformeService plateformeService;
    IUserService userService;

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
        plateformeService.save(c);
        userService.updatePlateformeId(c.getAgriculteur().getEmail(), c);
        return c;
    }

    @DeleteMapping("/remove-Plateforme/{Plateforme-id}")
    public void removePlateforme(@PathVariable("Plateforme-id") Long plateformeId) {
        Plateforme plateforme = plateformeService.findById(plateformeId);

        if (plateforme.getAgriculteur() != null) {
            userService.updatePlateformeId(plateforme.getAgriculteur().getEmail(), null);
        }

        plateformeService.delete(plateformeId);
    }

    @PutMapping("/update-Plateforme")
    public Plateforme updatePlateforme(@RequestBody Plateforme c) {
        return plateformeService.update(c);
    }




}

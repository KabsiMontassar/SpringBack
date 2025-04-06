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

    @PostMapping
    public Plateforme addPlateforme(@RequestBody Plateforme c) {
        plateformeService.save(c);
        userService.updatePlateformeId(c.getAgriculteur().getEmail(), c);
        return c;
    }

    @PutMapping
    public Plateforme updatePlateforme(@RequestBody Plateforme c) {
        Plateforme plateforme = plateformeService.findById(c.getIdPlateforme());
        c.setAgriculteur(plateforme.getAgriculteur());
        return plateformeService.update(c);
    }


    @DeleteMapping("/{id}")
    public void removePlateforme(@PathVariable("id") Long plateformeId) {
        Plateforme plateforme = plateformeService.findById(plateformeId);

        if (plateforme.getAgriculteur() != null) {
            userService.updatePlateformeId(plateforme.getAgriculteur().getEmail(), null);
        }

        plateformeService.delete(plateformeId);
    }
    @GetMapping("/{id}")
    public Plateforme retrievePlateforme(@PathVariable("id") Long PlateformeId) {
        return plateformeService.findById(PlateformeId);
    }




    @GetMapping
    public List<Plateforme> getPlateformes() {
        return plateformeService.findAll();
    }



}

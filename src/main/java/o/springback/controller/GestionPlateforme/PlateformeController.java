package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionPlateforme.Plateforme;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/plateforme")
public class PlateformeController {

    IPlateformeService plateformeService;
    IUserService userService;

    @PostMapping
    public Plateforme addPlateforme(@RequestBody Plateforme c) {
        if (c.getAgriculteur() == null || c.getAgriculteur().getEmail() == null) {
            throw new IllegalArgumentException("Agriculteur email is required");
        }

        Plateforme savedPlateforme = plateformeService.save(c);
        try {
            userService.updatePlateformeId(c.getAgriculteur().getEmail(), savedPlateforme);
        } catch (Exception e) {
            System.err.println("Error updating plateforme ID: " + e.getMessage());
        }
        return savedPlateforme;
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




    @PutMapping("/user/{id}/{plan}")
    public void changePackType(
            @PathVariable("id") Long id,
            @PathVariable("plan") String plan) {
        plateformeService.changePackType(id, plan);
    }



    @GetMapping("/generateReport")
    public Map<String,Integer> generateReport() {
       return plateformeService.generateReport();

    }

    @GetMapping("/mostlyBoughtPacks")
    public Map<String, Integer> getMostlyBoughtPacks() {
        return plateformeService.getMostlyBoughtPacks();
    }



}

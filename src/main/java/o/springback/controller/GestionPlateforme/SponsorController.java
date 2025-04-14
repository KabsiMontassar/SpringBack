package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.ISponsorService;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.entities.GestionPlateforme.Sponsor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sponsor")
public class SponsorController {
    ISponsorService sponsorService;

    @PostMapping("/")
    public Sponsor addsponsor(@RequestBody Sponsor c) {
        return sponsorService.save(c);
    }

    @PutMapping
    public Sponsor updatesponsor(@RequestBody Sponsor c) {
        return sponsorService.update(c);
    }

    @DeleteMapping("/{sponsor-id}")
    public void removesponsor(@PathVariable("sponsor-id") Long sponsorId) {
        sponsorService.delete(sponsorId);
    }

    @GetMapping("/{sponsor-id}")
    public Sponsor retrievesponsor(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.findById(sponsorId);
    }

    @GetMapping
    public List<Sponsor> getsponsor() {
        return sponsorService.findAll();
    }


    @GetMapping("/platform/{id}")
    public List<Sponsor> findByplateformeSponsor(@PathVariable("id") Long id) {
        return sponsorService.findByPlateformeSponsor_IdPlateforme(id);
    }









}

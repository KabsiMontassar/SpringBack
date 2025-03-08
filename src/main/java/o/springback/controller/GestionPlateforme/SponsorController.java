package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.ISponsorService;
import o.springback.entities.GestionPlateforme.Sponsor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sponsor")
public class SponsorController {
    ISponsorService sponsorService;

    @GetMapping("/retrieve-all-sponsor")
    public List<Sponsor> getsponsor() {
        return sponsorService.findAll();
    }
    @GetMapping("/retrieve-sponsor/{sponsor-id}")
    public Sponsor retrievesponsor(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.findById(sponsorId);
    }
    @PostMapping("/add-sponsor")
    public Sponsor addsponsor(@RequestBody Sponsor c) {
        return sponsorService.save(c);
    }
    @DeleteMapping("/remove-sponsor/{sponsor-id}")
    public void removesponsor(@PathVariable("sponsor-id") Long sponsorId) {
        sponsorService.delete(sponsorId);
    }
    @PutMapping("/update-sponsor")
    public Sponsor updatesponsor(@RequestBody Sponsor c) {
        return sponsorService.update(c);
    }
}

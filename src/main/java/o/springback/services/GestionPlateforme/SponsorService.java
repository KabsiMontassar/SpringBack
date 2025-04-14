package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.ISponsorService;
import o.springback.entities.GestionPlateforme.Sponsor;
import o.springback.repositories.GestionPlateformeRepository.SponsorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SponsorService implements ISponsorService {

    SponsorRepository sponsorRepository;

    @Override
    public Sponsor save(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    @Override
    public Sponsor update(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    @Override
    public void delete(Long id) {
        sponsorRepository.deleteById(id);
    }

    @Override
    public Sponsor findById(Long id) {
        return sponsorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sponsor> findAll() {
        return sponsorRepository.findAll();
    }

    @Override
    public List<Sponsor> findByplateformeSponsor(Long id) {
        return sponsorRepository.findByPlateformeSponsor_IdPlateforme(id);
    }

    @Override
    public List<Sponsor> findByPlateformeSponsor_IdPlateforme(Long id) {
        return sponsorRepository.findByPlateformeSponsor_IdPlateforme(id);
    }
}

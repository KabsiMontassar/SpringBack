package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Component;
import o.springback.entities.GestionPlateforme.Sponsor;

import java.util.List;

public interface ISponsorService {
    Sponsor save(Sponsor sponsor);
    Sponsor update(Sponsor sponsor);
    void delete(Long id);
    Sponsor findById(Long id);
    List<Sponsor> findAll();

    List<Sponsor> findByplateformeSponsor(Long id);
    List<Sponsor> findByPlateformeSponsor_IdPlateforme(Long id);
}

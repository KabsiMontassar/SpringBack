package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Plateforme;

import java.util.List;

public interface IPlateformeService {
    Plateforme save(Plateforme plateforme);
    Plateforme update(Plateforme plateforme);
    void delete(Long id);
    Plateforme findById(Long id);
    List<Plateforme> findAll();
}

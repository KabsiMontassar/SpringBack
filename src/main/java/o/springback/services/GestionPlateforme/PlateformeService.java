package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.repositories.GestionPlateformeRepository.PlateformeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PlateformeService implements IPlateformeService {

     private PlateformeRepository plateformeRepository;
    @Override
    public Plateforme save(Plateforme plateforme) {
         return plateformeRepository.save(plateforme);
    }

    @Override
    public Plateforme update(Plateforme plateforme) {
         return plateformeRepository.save(plateforme);
    }

    @Override
    public void delete(Long id) {
        plateformeRepository.deleteById(id);
    }

    @Override
    public Plateforme findById(Long id) {
        return plateformeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Plateforme> findAll() {
        return plateformeRepository.findAll();
    }
}

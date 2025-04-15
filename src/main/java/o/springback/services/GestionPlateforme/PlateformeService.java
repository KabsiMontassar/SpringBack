package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionPlateforme.TypePack;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionPlateformeRepository.PlateformeRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PlateformeService implements IPlateformeService {

    private PlateformeRepository plateformeRepository;
    private UserRepository userRepository;

    @Override
    public Plateforme save(Plateforme plateforme) {
        if (plateforme.getContent() == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        System.out.println("PlateformeService.save() called with: plateforme = [" + plateforme + "]");
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

    @Override
    public void changePackType(Long id, String plan) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {

            TypePack planEnum = TypePack.valueOf(plan.toUpperCase());

            user.setTypePack(planEnum);
            userRepository.save(user);

        }
    }

}

package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.springback.Interfaces.GestionPlateforme.IPlateformeService;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionPlateforme.TypePack;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionPlateformeRepository.PlateformeRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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



    @Scheduled(cron = "*/15 * * * * ?")
    public void checkPlateformeExpiration() {
        List<Plateforme> plateformes = plateformeRepository.findAll();
        log.info("Checking for plateforme expiration...");
        for (Plateforme plateforme : plateformes) {
            if (plateforme.getValabilite() != null
                    && plateforme.getValabilite().isBefore(java.time.LocalDate.now().plusDays(30))) {

                log.info("Plateforme " + plateforme.getNomPlateforme() +
                        " is about to expire on " + plateforme.getValabilite());
            }
        }
    }


    @Scheduled(cron = "*/15 * * * * ?")
    public void deleteExpiredPlateformes() {
        List<Plateforme> plateformes = plateformeRepository.findAll();
        log.info("Deleting expired plateformes...");
        for (Plateforme plateforme : plateformes) {
            if (plateforme.getValabilite() != null && plateforme.getValabilite().isBefore(java.time.LocalDate.now())) {
                plateformeRepository.delete(plateforme);
               log.info("Plateforme " + plateforme.getNomPlateforme()
                       + " has been deleted due to expiration on " + plateforme.getValabilite());
            }
        }
    }


    public Map<String,Integer> generateReport() {
        Map<String,Integer> result = new HashMap<>();
        List<Plateforme> plateformes = plateformeRepository.findAll();
        Integer totalPlateformes = plateformes.size();
        Integer activePlateformes = (int) plateformes.stream()
                .filter(plateforme -> plateforme.getValabilite() != null &&
                        plateforme.getValabilite().isAfter(java.time.LocalDate.now())).count();

        Integer expiredPlateformes = totalPlateformes - activePlateformes;

        result.put("TotalPlateformes", totalPlateformes);
        result.put("ActivePlateformes", activePlateformes);
        result.put("ExpiredPlateformes", expiredPlateformes);
        return result;
    }




}

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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public Plateforme save(Plateforme plateforme) {
        if (plateforme.getContent() == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        log.debug("PlateformeService.save() called with: plateforme = [{}]", plateforme);
        return plateformeRepository.saveAndFlush(plateforme);
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

    @Override
    public Map<String, Integer> getMostlyBoughtPacks() {
        List<User> users = userRepository.findAll();
        Map<TypePack, Integer> packCount = new HashMap<>();


        for (TypePack pack : TypePack.values()) {
            packCount.put(pack, 0);
        }

        for (User user : users) {
            TypePack pack = user.getTypePack();
            if (pack != null) {
                packCount.put(pack, packCount.get(pack) + 1);
            }
        }

        List<Map.Entry<TypePack, Integer>> sortedPacks = new ArrayList<>(packCount.entrySet());

        sortedPacks.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<TypePack, Integer> entry : sortedPacks) {
            result.put(entry.getKey().name(), entry.getValue());
        }

        return result;
    }


    //@Scheduled(cron = "*/15 * * * * ?")
    public void checkPlateformeExpiration() {
        List<Plateforme> plateformes = plateformeRepository.findAll();
        log.info("Checking for plateforme expiration...");
        for (Plateforme plateforme : plateformes) {
            if (plateforme.getValabilite() != null
                    && plateforme.getValabilite().isBefore(java.time.LocalDate.now().plusDays(30))) {

                log.info("Plateforme {} is about to expire on {}", plateforme.getNomPlateforme(), plateforme.getValabilite());
            }
        }
    }


   //@Scheduled(cron = "*/15 * * * * ?")
    public void deleteExpiredPlateformes() {
        List<Plateforme> plateformes = plateformeRepository.findAll();
        log.info("Deleting expired plateformes...");
        for (Plateforme plateforme : plateformes) {
            if (plateforme.getValabilite() != null && plateforme.getValabilite().isBefore(java.time.LocalDate.now())) {
                plateformeRepository.delete(plateforme);
                log.info("Plateforme {} has been deleted due to expiration on {}", plateforme.getNomPlateforme(), plateforme.getValabilite());
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

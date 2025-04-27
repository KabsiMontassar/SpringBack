package o.springback.services.GestionFormation;

import lombok.extern.slf4j.Slf4j;
import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FormationService implements IFormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get("uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "uploads/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formation addFormation(Formation formation, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo);
            formation.setPhotoPath(photoPath);
        }
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(int id, Formation formation, MultipartFile photo) {
        formation.setIdFormation(id);
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo);
            formation.setPhotoPath(photoPath);
        }
        return formationRepository.save(formation);
    }

    @Override
    public void deleteFormation(int id) {
        formationRepository.deleteById(id);
    }

    @Override
    public Formation getFormationById(int id) {
        return formationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Object[] obtenirTauxReussite(int formationId) {
        return participationRepository.getTauxReussiteFormation(formationId);
    }

    //@Scheduled(cron = "*/15 * * * * *")
    public void afficherFormationsDeDemain() {
        LocalDate demain = LocalDate.now().plusDays(1);

        for (Formation f : formationRepository.findAll()) {
            Date dateDebut = f.getDateDebut();
            if (dateDebut == null) continue;
            LocalDate dateDebutLocal = new java.sql.Date(dateDebut.getTime()).toLocalDate();
            if (dateDebutLocal.isEqual(demain)) {
                log.info(" Formation prévue demain : "+f.getNom());
            }
        }

    }


    //@Scheduled(fixedRate = 30000)
    public void rappelerFormationsSansDetails() {
        for (Formation f : formationRepository.findAll()) {
            if (f.getDetailFormation() == null) {
                log.warn(" Formation sans détails : " + f.getNom() + " (ID: " + f.getIdFormation() + ")");
            }
        }
    }

}

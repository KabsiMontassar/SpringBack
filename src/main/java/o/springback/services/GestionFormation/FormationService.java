package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FormationService implements IFormationService {

    @Autowired
    private FormationRepository formationRepository;

    // Méthode pour sauvegarder le fichier
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
        // Si une image est fournie, la sauvegarder et définir le chemin
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo); // Utilisation de saveFile avec MultipartFile
            formation.setPhotoPath(photoPath);
        }
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(int id, Formation formation, MultipartFile photo) {
        formation.setIdFormation(id);
        // Si une nouvelle image est fournie, la sauvegarder et définir le chemin
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo); // Utilisation de saveFile avec MultipartFile
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

    @Autowired
    private ParticipationRepository participationRepository;


    public Object[] obtenirTauxReussite(int formationId) {
        return participationRepository.getTauxReussiteFormation(formationId);
    }
}

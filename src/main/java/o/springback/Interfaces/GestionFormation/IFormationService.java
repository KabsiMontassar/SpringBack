package o.springback.Interfaces.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFormationService {
    Formation addFormation(Formation formation, MultipartFile photo); // Modifié pour accepter un fichier
    Formation updateFormation(int id, Formation formation, MultipartFile photo); // Modifié pour accepter un fichier
    void deleteFormation(int id);
    Formation getFormationById(int id);
    List<Formation> getAllFormations();
    String saveFile(MultipartFile file);

    Object[] obtenirTauxReussite(int formationId);
}

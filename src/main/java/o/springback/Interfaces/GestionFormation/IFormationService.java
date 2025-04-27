package o.springback.Interfaces.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.TypeFormation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFormationService {
    Formation addFormation(Formation formation, MultipartFile photo);
    Formation updateFormation(int id, Formation formation, MultipartFile photo);
    void deleteFormation(int id);
    Formation getFormationById(int id);
    List<Formation> getAllFormations();
    String saveFile(MultipartFile file);

    Object[] obtenirTauxReussite(int formationId);

    List<Formation> getFormationsByType(TypeFormation type);

    ;
}

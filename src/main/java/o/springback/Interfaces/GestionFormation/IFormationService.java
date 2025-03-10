package o.springback.Interfaces.GestionFormation;

import o.springback.entities.GestionFormation.Formation;

import java.util.List;

public interface IFormationService {
    Formation addFormation(Formation formation);
    Formation updateFormation(int id, Formation formation);
    void deleteFormation(int id);
    Formation getFormationById(int id);
    List<Formation> getAllFormations();
}

package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IFormationService;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService implements IFormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Override
    public Formation addFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(int id, Formation formation) {
        formation.setIdFormation(id);
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
}
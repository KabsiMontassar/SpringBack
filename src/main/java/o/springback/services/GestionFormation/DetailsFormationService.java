package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IDetailsFormationService;
import o.springback.entities.GestionFormation.DetailsFormation;
import o.springback.repositories.GestionFormation.DetailsFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsFormationService implements IDetailsFormationService {


    @Autowired
    private DetailsFormationRepository detailFormationRepository;

    @Override
    public DetailsFormation addDetailFormation(DetailsFormation detailFormation) {
        return detailFormationRepository.save(detailFormation);
    }

    @Override
    public DetailsFormation updateDetailFormation(int id, DetailsFormation detailFormation) {
        detailFormation.setIdDetaille(id); // Assurez-vous que l'ID est correct
        return detailFormationRepository.save(detailFormation);
    }

    @Override
    public void deleteDetailFormation(int id) {
        detailFormationRepository.deleteById(id);
    }

    @Override
    public DetailsFormation getDetailFormationById(int id) {
        return detailFormationRepository.findById(id).orElse(null);
    }
}
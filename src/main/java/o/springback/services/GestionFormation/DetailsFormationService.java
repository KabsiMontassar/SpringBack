package o.springback.services.GestionFormation;

import o.springback.Interfaces.GestionFormation.IDetailsFormationService;
import o.springback.entities.GestionFormation.DetailsFormation;
import o.springback.entities.GestionFormation.Formation;
import o.springback.repositories.GestionFormation.DetailsFormationRepository;
import o.springback.repositories.GestionFormation.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsFormationService implements IDetailsFormationService {

    @Autowired
    private DetailsFormationRepository detailFormationRepository;

    @Autowired
    private FormationRepository formationRepository; // Ajout du repository pour Formation

    @Override
    public DetailsFormation addDetailFormation(DetailsFormation detailFormation) {
        // Vérifie si la formation associée existe avant d'ajouter un détail
        if (!formationRepository.existsById(detailFormation.getFormation().getIdFormation())) {
            throw new RuntimeException("La formation associée n'existe pas.");
        }

        return detailFormationRepository.save(detailFormation);
    }

    @Override
    public DetailsFormation updateDetailFormation(int id, DetailsFormation detailFormation) {
        detailFormation.setIdDetaille(id); // Assurez-vous que l'ID est correct
        return detailFormationRepository.save(detailFormation);
    }

    @Override
    public void deleteDetailFormation(int id) {
        Optional<DetailsFormation> detailOpt = detailFormationRepository.findById(id);
        if (detailOpt.isPresent()) {
            DetailsFormation detail = detailOpt.get();
            Formation formation = detail.getFormation();

            if (formation != null) {
                formation.setDetailFormation(null); // important : coupe la relation
                formationRepository.save(formation); // Hibernate comprend qu'on supprime le détail
            }

            detailFormationRepository.delete(detail); // peut même être omis, car orphanRemoval s’en charge
            System.out.println(">>> Détail supprimé avec succès via Formation !");
        } else {
            System.out.println(">>> Aucun détail trouvé avec l'ID : " + id);
        }
    }

    @Override
    public DetailsFormation getDetailFormationById(int id) {
        return detailFormationRepository.findById(id).orElse(null);
    }
}

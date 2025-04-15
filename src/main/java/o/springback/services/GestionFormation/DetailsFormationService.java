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
    private FormationRepository formationRepository;

    @Override
    public DetailsFormation addDetailFormation(DetailsFormation detailFormation) {
        int formationId = detailFormation.getFormation().getIdFormation();
        Optional<Formation> formationOpt = formationRepository.findById(formationId);

        if (formationOpt.isEmpty()) {
            throw new RuntimeException("❌ La formation avec ID " + formationId + " n'existe pas.");
        }

        Formation formation = formationOpt.get();

        if (formation.getDetailFormation() != null) {
            throw new RuntimeException("❌ Cette formation a déjà des détails !");
        }

        // ✅ Lier les deux objets
        formation.setDetailFormation(detailFormation);
        detailFormation.setFormation(formation);

        // ✅ Hibernate gère le cascade grâce à `cascade = CascadeType.ALL`
        return formationRepository.save(formation).getDetailFormation();
    }

    @Override
    public DetailsFormation updateDetailFormation(int id, DetailsFormation detailFormation) {
        Optional<DetailsFormation> existingOpt = detailFormationRepository.findById(id);

        if (existingOpt.isEmpty()) {
            throw new RuntimeException("❌ Aucune détail trouvé avec l'ID " + id);
        }

        DetailsFormation existing = existingOpt.get();

        // ✅ Mise à jour des champs (ne jamais réécraser tout !)
        existing.setObjectif(detailFormation.getObjectif());
        existing.setContenu(detailFormation.getContenu());
        existing.setDuree(detailFormation.getDuree());
        existing.setMaterielRequis(detailFormation.getMaterielRequis());

        return detailFormationRepository.save(existing);
    }

    @Override
    public void deleteDetailFormation(int id) {
        Optional<DetailsFormation> detailOpt = detailFormationRepository.findById(id);

        if (detailOpt.isPresent()) {
            DetailsFormation detail = detailOpt.get();
            Formation formation = detail.getFormation();

            if (formation != null) {
                formation.setDetailFormation(null); // Rompre la relation
                formationRepository.save(formation); // Hibernate gère le reste
            }

            detailFormationRepository.delete(detail); // Suppression effective
            System.out.println("✅ Détail supprimé !");
        } else {
            System.out.println("❌ Aucun détail trouvé avec ID " + id);
        }
    }

    @Override
    public DetailsFormation getDetailFormationById(int id) {
        return detailFormationRepository.findById(id).orElse(null);
    }
}

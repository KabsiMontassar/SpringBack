package o.springback.services.GestionFormation;

import lombok.AllArgsConstructor;
import o.springback.repositories.GestionFormation.DetailsFormationRepository;
import o.springback.repositories.GestionFormation.FormationRepository;
import o.springback.repositories.GestionFormation.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatistiqueService {

    private ParticipationRepository participationRepository;
    private FormationRepository formationRepository;
    private DetailsFormationRepository detailsFormationRepository;

    public List<Object[]> getTauxReussiteParFormation() {
        return participationRepository.tauxDeReussiteParFormation();
    }

    public List<Object[]> getFormationsPopulaires() {
        return participationRepository.formationsPopulaires();
    }

    public List<Object[]> getMoyenneNotesParFormation() {
        return participationRepository.moyenneNotesParFormation();
    }


    public long getTotalFormations() {
        return formationRepository.countTotalFormations();
    }

    public long getFormationsCertifiantes() {
        return formationRepository.countFormationsCertifiantes();
    }

    public Double getMoyenneCapacite() {
        return formationRepository.moyenneCapacite();
    }

    public List<Object[]> getFormationsParType() {
        return formationRepository.countByType();
    }


    public Double getMoyenneDureeDetails() {
        return detailsFormationRepository.moyenneDureeDetails();
    }

    public long getFormationsSansDetails() {
        return detailsFormationRepository.countFormationsSansDetails();
    }
}

package o.springback.services.GestionFormation;

import o.springback.repositories.GestionFormation.ParticipationRepository;

import java.util.List;

public class StatistiqueService {
    private  ParticipationRepository participationRepository;

    public List<Object[]> getTauxReussiteParFormation() {
        return participationRepository.tauxDeReussiteParFormation();
    }

    public List<Object[]> getFormationsPopulaires() {
        return participationRepository.formationsPopulaires();
    }

    public List<Object[]> getMoyenneNotesParFormation() {
        return participationRepository.moyenneNotesParFormation();
    }
}

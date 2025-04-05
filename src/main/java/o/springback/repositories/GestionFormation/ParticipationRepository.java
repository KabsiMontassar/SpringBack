package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    @Query("""
            SELECT f.nom AS formationNom, 
                   AVG(p.noteFinale) AS moyenneNote, 
                   COUNT(p.idParticipation) AS totalParticipants
            FROM Participation p
            JOIN p.formation f
            WHERE f.idFormation = :formationId
            GROUP BY f.idFormation
            """)
    Object[] getTauxReussiteFormation(int formationId);
}
package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionUser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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


    boolean existsByUserAndFormation(User user, Formation formation);

    @Query("""
           SELECT f.nom, 
                  COUNT(p), 
                  SUM(CASE WHEN p.certificatDelivre = true THEN 1 ELSE 0 END) 
           FROM Participation p JOIN p.formation f 
           GROUP BY f.idFormation, f.nom
           """)
    List<Object[]> tauxDeReussiteParFormation();

    @Query("""
           SELECT f.nom, 
                  COUNT(p.idParticipation) as nb 
           FROM Participation p 
           JOIN p.formation f 
           GROUP BY f.idFormation, f.nom 
           ORDER BY nb DESC
           """)
    List<Object[]> formationsPopulaires();


    @Query("""
           SELECT f.nom, AVG(p.noteFinale) 
           FROM Participation p 
           JOIN p.formation f 
           GROUP BY f.nom
           """)
    List<Object[]> moyenneNotesParFormation();


    List<Participation> findByFormationAndEnAttente(Formation formation, boolean enAttente);

    long countByFormation(Formation formation);


    List<Participation> findByUser(User user);

    List<Participation> findByFormationAndEnAttenteOrderByDateInscriptionAsc(Formation formation, boolean enAttente);
    @Query("""
       SELECT COUNT(p)
       FROM Participation p
       WHERE p.formation.idFormation = :formationId AND p.enAttente = false
       """)

    long countConfirmedParticipantsByFormationId(int formationId);





}

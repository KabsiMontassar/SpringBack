package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionUser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    // 📊 Taux de réussite d’une formation donnée
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

    // ✅ Vérifie si un utilisateur a déjà participé à une formation
    boolean existsByUserAndFormation(User user, Formation formation);

    // 📊 Taux de réussite de toutes les formations
    @Query("""
           SELECT f.nom, 
                  COUNT(p), 
                  SUM(CASE WHEN p.certificatDelivre = true THEN 1 ELSE 0 END) 
           FROM Participation p JOIN p.formation f 
           GROUP BY f.idFormation, f.nom
           """)
    List<Object[]> tauxDeReussiteParFormation();

    // 📈 Formations les plus populaires
    @Query("""
           SELECT f.nom, 
                  COUNT(p.idParticipation) as nb 
           FROM Participation p 
           JOIN p.formation f 
           GROUP BY f.idFormation, f.nom 
           ORDER BY nb DESC
           """)
    List<Object[]> formationsPopulaires();

    // 🧮 Moyenne des notes par formation
    @Query("""
           SELECT f.nom, AVG(p.noteFinale) 
           FROM Participation p 
           JOIN p.formation f 
           GROUP BY f.nom
           """)
    List<Object[]> moyenneNotesParFormation();

    // 🔍 Liste d’attente pour une formation
    List<Participation> findByFormationAndEnAttente(Formation formation, boolean enAttente);

    // 🔢 Nombre total de participants à une formation
    long countByFormation(Formation formation);

    // 🆕 Liste des participations d’un utilisateur connecté
    List<Participation> findByUser(User user);

    List<Participation> findByFormationAndEnAttenteOrderByDateInscriptionAsc(Formation formation, boolean enAttente);
    @Query("""
       SELECT COUNT(p)
       FROM Participation p
       WHERE p.formation.idFormation = :formationId AND p.enAttente = false
       """)
    long countConfirmedParticipantsByFormationId(int formationId);





}

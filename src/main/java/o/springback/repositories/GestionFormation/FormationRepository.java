package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.TypeFormation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
    @EntityGraph(attributePaths = "detailFormation")
    List<Formation> findAll();

    @Query("SELECT COUNT(f) FROM Formation f")
    long countTotalFormations();

    @Query("SELECT COUNT(f) FROM Formation f WHERE f.certification = true")
    long countFormationsCertifiantes();
    @Query("SELECT AVG(f.Capacity) FROM Formation f")
    Double moyenneCapacite();

    @Query("SELECT f.typeFormation, COUNT(f) FROM Formation f GROUP BY f.typeFormation")
    List<Object[]> countByType();


    List<Formation> findByTypeFormation(TypeFormation typeFormation);
}

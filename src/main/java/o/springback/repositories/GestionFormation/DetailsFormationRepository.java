package o.springback.repositories.GestionFormation;

import o.springback.entities.GestionFormation.DetailsFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DetailsFormationRepository extends JpaRepository<DetailsFormation, Integer> {

    @Query("SELECT AVG(d.duree) FROM DetailsFormation d")
    Double moyenneDureeDetails();

    @Query("SELECT COUNT(f) FROM Formation f WHERE f.detailFormation IS NULL")
    long countFormationsSansDetails();

}

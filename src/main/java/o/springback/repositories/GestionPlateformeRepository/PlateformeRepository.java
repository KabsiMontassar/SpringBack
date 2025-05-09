package o.springback.repositories.GestionPlateformeRepository;

import o.springback.entities.GestionPlateforme.Plateforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateformeRepository extends JpaRepository<Plateforme, Long> {
    Plateforme findByNomPlateforme(String name);

    // public void updateTheme(Long plateformeId, String newTheme) ;

  //  public void desactiverPlateformeExpires();

   // public void generateReport();
}

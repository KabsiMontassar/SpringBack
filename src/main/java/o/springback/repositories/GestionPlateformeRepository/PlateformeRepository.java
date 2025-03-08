package o.springback.repositories.GestionPlateformeRepository;

import o.springback.entities.GestionPlateforme.Plateforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateformeRepository extends JpaRepository<Plateforme, Long> {
}

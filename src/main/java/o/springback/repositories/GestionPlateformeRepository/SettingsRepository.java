package o.springback.repositories.GestionPlateformeRepository;

import o.springback.entities.GestionPlateforme.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
}

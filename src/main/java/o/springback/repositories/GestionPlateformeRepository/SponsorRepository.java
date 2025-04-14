package o.springback.repositories.GestionPlateformeRepository;

import o.springback.entities.GestionPlateforme.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    List<Sponsor> findByPlateformeSponsor_IdPlateforme(Long id);
}

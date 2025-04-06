package o.springback.repositories.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Query("SELECT c FROM Component c WHERE c.user.idUser = ?1")
    List<Component> findByIdUser(Long userId);
}

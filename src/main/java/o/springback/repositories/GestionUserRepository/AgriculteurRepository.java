package o.springback.repositories.GestionUserRepository;
import o.springback.entities.GestionUser.Agriculteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AgriculteurRepository extends JpaRepository<Agriculteur, Long>{
}

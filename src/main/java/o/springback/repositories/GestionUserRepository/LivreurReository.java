package o.springback.repositories.GestionUserRepository;
import o.springback.entities.GestionUser.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LivreurReository extends JpaRepository<Livreur, Long>{

}

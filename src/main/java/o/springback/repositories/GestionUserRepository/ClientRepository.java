package o.springback.repositories.GestionUserRepository;

import o.springback.entities.GestionUser.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}

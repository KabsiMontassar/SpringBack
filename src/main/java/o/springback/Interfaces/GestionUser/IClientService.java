package o.springback.Interfaces.GestionUser;
import o.springback.entities.GestionUser.Client;
import o.springback.entities.GestionUser.User;

import java.util.List;
public interface IClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    void delete(Long id);
    Client update(Client client);
}

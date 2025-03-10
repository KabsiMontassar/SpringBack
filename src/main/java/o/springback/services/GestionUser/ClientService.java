package o.springback.services.GestionUser;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IClientService;
import o.springback.entities.GestionUser.Client;
import o.springback.repositories.GestionUserRepository.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class ClientService implements IClientService{
    private ClientRepository clientRepository;
    @Override
    public Client save (Client client){
        return clientRepository.save(client);
    }
    @Override
    public Client update(Client client){
        return clientRepository.save(client);
    }
    @Override
    public void delete(Long id){
        clientRepository.deleteById(id);
    }
    @Override
    public Client findById(Long id){
        return clientRepository.findById(id).orElse(null);
    }
    @Override
    public List<Client> findAll(){
        return clientRepository.findAll();
    }


}

package o.springback.services.GestionUser;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IAgriculteurService;
import o.springback.entities.GestionUser.Agriculteur;
import o.springback.repositories.GestionUserRepository.AgriculteurRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class AgriculteurService implements IAgriculteurService{
    private AgriculteurRepository agriculteurRepository;
    @Override
    public Agriculteur save (Agriculteur agriculteur){
        return agriculteurRepository.save(agriculteur);
    }
    @Override
    public Agriculteur update(Agriculteur agriculteur){
        return agriculteurRepository.save(agriculteur);
    }
    @Override
    public void delete(Long id){
        agriculteurRepository.deleteById(id);
    }
    @Override
    public Agriculteur findById(Long id){
        return agriculteurRepository.findById(id).orElse(null);
    }
    @Override
    public List<Agriculteur> findAll(){
        return agriculteurRepository.findAll();
    }
}

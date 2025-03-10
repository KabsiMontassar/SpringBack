package o.springback.services.GestionUser;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.ILivreurService;
import o.springback.entities.GestionUser.Livreur;
import o.springback.repositories.GestionUserRepository.LivreurReository;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class LivreurService implements ILivreurService {
    private LivreurReository livreurReository;
    @Override
    public Livreur save (Livreur livreur){
        return livreurReository.save(livreur);
    }
    @Override
    public Livreur update(Livreur livreur){
        return livreurReository.save(livreur);
    }
    @Override
    public void delete(Long id){
        livreurReository.deleteById(id);
    }
    @Override
    public Livreur findById(Long id){
        return livreurReository.findById(id).orElse(null);
    }
    @Override
    public List<Livreur> findAll(){
        return livreurReository.findAll();
    }

}

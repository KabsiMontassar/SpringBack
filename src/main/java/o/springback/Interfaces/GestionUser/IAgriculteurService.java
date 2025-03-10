package o.springback.Interfaces.GestionUser;
import o.springback.entities.GestionUser.Agriculteur;
import java.util.List;
public interface IAgriculteurService {
    List<Agriculteur> findAll();
    Agriculteur findById(Long id);
    Agriculteur save(Agriculteur agriculteur);
    void delete(Long id);
    Agriculteur update(Agriculteur agriculteur);
}

package o.springback.Interfaces.GestionUser;
import o.springback.entities.GestionUser.Livreur;
import java.util.List;
public interface ILivreur {
    List<Livreur> findAll();
    Livreur findById(Long id);
    Livreur save(Livreur livreur);
    void delete(Long id);
    Livreur update(Livreur livreur);
}

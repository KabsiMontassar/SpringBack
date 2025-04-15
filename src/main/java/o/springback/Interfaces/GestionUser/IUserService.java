package o.springback.Interfaces.GestionUser;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionUser.User;
import java.util.List;

public interface IUserService {
    String addUser(User userInfo);
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void delete(Long id);
    User update(User user);

    User findByEmail(String email);

    void updatePlateformeId(String email, Plateforme c);
}

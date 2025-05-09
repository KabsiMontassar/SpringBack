package o.springback.Interfaces.GestionUser;
import o.springback.dto.RegisterRequestDTO;
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
    User mapToDTO(User user);
    User register (RegisterRequestDTO request);
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
    void changePassword(Long id, String currentPassword, String newPassword);


    void updatePlateformeId(String email, Plateforme c);
}

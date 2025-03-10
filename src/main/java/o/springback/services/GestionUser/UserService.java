package o.springback.services.GestionUser;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private UserRepository userRepository;
    @Override
    public User save (User user){
        return userRepository.save(user);
    }
    @Override
    public User update(User user){
        return userRepository.save(user);
    }
    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
}

package o.springback.services.GestionUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionUser.Agriculteur;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService implements IUserService , UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void updatePlateformeId(String email, Plateforme plateforme) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            user.setPlateforme(plateforme);
            userRepository.save(user);
        }
    }


    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public List<User> findAll(){
        return  userRepository.findAll();
    }




    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByEmail(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }




    public String addUser(User userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return "User Added Successfully";
    }
}

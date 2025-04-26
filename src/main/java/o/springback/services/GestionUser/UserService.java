package o.springback.services.GestionUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.dto.RegisterRequestDTO;
import o.springback.dto.UserDTO;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionUser.Agriculteur;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import o.springback.services.GestionUser.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class UserService implements IUserService , UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

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
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
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
    public User register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTelephone(request.getTelephone());
        user.setAdresse(request.getAdresse());
        user.setDateInscription(new Date());
        user.setRole("ROLE_USER");


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

    public void forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new RuntimeException("Aucun utilisateur trouvé avec cet email");
        }
        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);
        if (optionalUser.isEmpty()){
            throw new RuntimeException("Token invalide ou expiré");
        }
        User user = optionalUser.get();
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("Le nouveau mot de passe ne peut pas être le même que l'ancien");
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setVerificationToken(null);
            userRepository.save(user);
        }
    }

    @Override
    public void changePassword(Long id, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Aucun utilisateur trouvé avec cet ID");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("Le nouveau mot de passe ne peut pas être le même que l'ancien");
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }
}

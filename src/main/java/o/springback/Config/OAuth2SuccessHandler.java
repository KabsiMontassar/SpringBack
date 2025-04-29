package o.springback.Config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionUserRepository.UserRepository;
import o.springback.services.GestionUser.EmailService;
import o.springback.services.GestionUser.JwtService;
import o.springback.services.GestionUser.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private  final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService; //final khater used in lambda expression

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String providerId = oauthUser.getAttribute("id");

        if(email == null || email.isBlank()) {
            email = "user_" + providerId + "@facebookuser.com";
        }

        final String finalEmail = email;

        User user = userRepository.findByEmail(email) //ken l'email existant nekhdhou les données mtaa l user mel bdd w tssir authentification normale
                .orElseGet(() -> { //si non naamlou user jdid bel nom w prénom mtaa li aand l provider (google wala fb)
                    User newUser = new User();
                    newUser.setEmail(finalEmail);
                    newUser.setNom(name != null ? name.split(" ")[0] : "Google"); //on récupère le nom du user si non nhotoulou Google par défaut
                    newUser.setPrenom(name != null && name.split(" ").length> 1 ?name.split(" ")[1] : "User"); //on récupère le prénom si non nhotoulou User par défaut
                    newUser.setPassword(UUID.randomUUID().toString());
                    newUser.setDateInscription(new Date());
                    newUser.setRole("ROLE_CLIENT");
                    newUser.setVerified(true);
                    emailService.sendWelcomeEmail(finalEmail, newUser.getPrenom());
                    return userRepository.save(newUser);
                });
        String token = jwtService.generateToken(user.getEmail());

        response.sendRedirect("http://localhost:4200/oauth-success?token=" + token);

    }
}

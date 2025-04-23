package o.springback.services.GestionUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("molkaboughanmi3@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    public void sendVerificationEmail(String to, String prenom, String token){
        String subject = "Bienvenue sur Farmie - Veuillez Vérifier Votre Email";
        String verificationLink = "http://localhost:8081/users/verify?token=" + token;
        String text = "Cher(e) " + prenom + ",\n\n" +
                "Veuillez cliquer sur le lien ci-dessous pour vérifier votre email et activer votre compte:\n\n" +
                verificationLink;
        sendSimpleEmail(to, subject, text);
    }
    public void sendWelcomeEmail(String to, String prenom) {
        String subject = "Bienvenue sur Farmie!";
        String text = "Cher(e) " + prenom + ",\n\n" +
                "Bienvenue sur Farmie! Nous sommes ravis de vous avoir parmi nous.";
        sendSimpleEmail(to, subject, text);
    }
    public void sendResetPasswordEmail(String to, String token) {
        String subject = "Demande de Réinitialisation de Mot de Passe - Farmie";
        String resetLink = "http://localhost:4200/frontoffice/reset-password?token=" + token;
        String text = "Bonjour,\n\n" +
                "Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe:\n" +
                resetLink + "\n\n" +
                "Si vous n'avez pas demandé de réinitialisation de mot de passe, veuillez ignorer cet email.";
        sendSimpleEmail(to, subject, text);
    }
}

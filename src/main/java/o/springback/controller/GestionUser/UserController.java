package o.springback.controller.GestionUser;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.dto.AuthenticationResponseDTO;
import o.springback.dto.RegisterRequestDTO;
import o.springback.entities.GestionUser.AuthRequest;
import o.springback.entities.GestionUser.User;
import o.springback.services.GestionUser.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")

public class UserController {

        IUserService userService;
        private JwtService jwtService;
       private AuthenticationManager authenticationManager;


        @GetMapping("/retrieve-all-Users")
        public List<User> getUsers() {
            return userService.findAll();
        }
        @GetMapping("/retrieve-User/{User-id}")
        public User retrieveUser(@PathVariable("User-id") Long UserId) {
            return userService.findById(UserId);
        }
    @PostMapping("/addNewUser")
    public String addUser(@RequestBody User c) {

            return userService.addUser(c);
    }
        @DeleteMapping("/user/remove-User/{User-id}")
        public void removeUser(@PathVariable("User-id") Long UserId) {
            userService.delete(UserId);
        }
        @PutMapping("/update-User")
        public User updateUser(@RequestBody User c) {
            return userService.update(c);
        }

        @GetMapping("/welcome")
        public String welcome() {
            return "Welcome this endpoint is not secure";
        }

        @GetMapping("/user/userProfile")
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public String userProfile() {
            return "Welcome to User Profile";
        }

        @GetMapping("/admin/adminProfile")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public String adminProfile() {
            return "Welcome to Admin Profile";
        }

       @PostMapping("/generateToken")
        public ResponseEntity<AuthenticationResponseDTO> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                User user = userService.findByEmail(authRequest.getUsername());
                return ResponseEntity.ok(new AuthenticationResponseDTO(token, user));
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
    }
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid RegisterRequestDTO request) {
        try {
            User user = userService.register(request);
            String token = jwtService.generateToken(user.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
            try{
                userService.forgotPassword(email);
                return ResponseEntity.ok("Email de réinitialisation envoyé.");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String currentPassword, @RequestParam String newPassword){
            try {
                userService.changePassword(id, currentPassword, newPassword);
                return ResponseEntity.ok("Mot de passe changé avec succès.");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }
}
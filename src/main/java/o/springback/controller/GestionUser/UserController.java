package o.springback.controller.GestionUser;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionUser.AuthRequest;
import o.springback.entities.GestionUser.User;
import o.springback.services.GestionUser.JwtService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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



    @GetMapping("/{User-email}")
    public User retrieveUserbyemail(@PathVariable("User-email") String email) {
        return userService.findByEmail(email);
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
        public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String sanitizedUsername = org.springframework.web.util.HtmlUtils.htmlEscape(authRequest.getUsername());
                return jwtService.generateToken(sanitizedUsername);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
    }
}
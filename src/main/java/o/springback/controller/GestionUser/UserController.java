package o.springback.controller.GestionUser;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionUser.IUserService;
import o.springback.entities.GestionUser.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

        IUserService userService;

        @GetMapping("/retrieve-all-Users")
        public List<User> getUsers() {
            return userService.findAll();
        }
        @GetMapping("/retrieve-User/{User-id}")
        public User retrieveUser(@PathVariable("User-id") Long UserId) {
            return userService.findById(UserId);
        }
        @PostMapping("/add-User")
        public User addUser(@RequestBody User c) {
            return userService.save(c);
        }
        @DeleteMapping("/remove-User/{User-id}")
        public void removeUser(@PathVariable("User-id") Long UserId) {
            userService.delete(UserId);
        }
        @PutMapping("/update-User")
        public User updateUser(@RequestBody User c) {
            return userService.update(c);
        }
}

package o.springback.services.Interfaces;

import o.springback.entities.gestionUser.User;

import java.util.List;

public interface IUserService {
    User save(User user);
    User update(User user);
    void delete(Long id);
    User findById(Long id);
    List<User> findAll();
}

package o.springback.services.GestionCommande;

import jakarta.transaction.Transactional;
import o.springback.Interfaces.GestionCommande.OrderService;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionUserRepository.UserRepository;
import o.springback.repositories.Gestioncommande.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Order> getUserOrders() {
        User currentUser = getCurrentUser();
        if (currentUser.getRole().equals("ADMIN")) {
            return this.orderRepo.findAll();
        }

        return orderRepo.findByUser(currentUser);
    }

    public List<Order> getAllOrders() {
        return this.orderRepo.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        order.setUser(getCurrentUser());
        return this.orderRepo.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepo.save(order);
    }

    @Override
    public void deleteCommande(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepo.findByStatus(status);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();

        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }


}
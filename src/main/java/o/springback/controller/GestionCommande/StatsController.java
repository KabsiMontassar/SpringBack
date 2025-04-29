package o.springback.controller.GestionCommande;

import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionCommande.OrderService;
import o.springback.dto.GestionCommande.TopProductDTO;
import o.springback.entities.GestionCommande.StatusPaiement;
import o.springback.repositories.Gestioncommande.OrderProductRepository;
import o.springback.repositories.Gestioncommande.OrderRepo;
import o.springback.repositories.Gestioncommande.PaiementRepository;
import o.springback.services.GestionCommande.OrderServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import o.springback.entities.GestionCommande.Payment;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final OrderRepo orderRepository;
    private final PaiementRepository paymentRepository;
    private final OrderProductRepository orderProductRepository;

    @GetMapping("/revenue")
    public Double getTotalRevenue() {
        return paymentRepository.findAll().stream()
                .filter(p -> StatusPaiement.SUCCEEDED.equals(p.getStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @GetMapping("/orders/count")
    public Long getOrderCount() {
        return orderRepository.count();
    }

    @GetMapping("/orders/average")
    public Double getAverageOrderValue() {
        long count = orderRepository.count();
        if (count == 0) return 0D;
        double revenue = getTotalRevenue();
        return revenue / count;
    }

    @GetMapping("/products/top")
    public List<TopProductDTO> getTopProducts() {
        return orderProductRepository.findTopSellingProducts(PageRequest.of(0, 5));
    }
}



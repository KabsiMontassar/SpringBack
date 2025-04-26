package o.springback.controller.GestionCommande;

import lombok.RequiredArgsConstructor;
import o.springback.repositories.Gestioncommande.OrderProductRepository;
import o.springback.repositories.Gestioncommande.OrderRepo;
import o.springback.repositories.Gestioncommande.PaiementRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

}

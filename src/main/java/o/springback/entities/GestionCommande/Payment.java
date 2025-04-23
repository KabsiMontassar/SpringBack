package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "paiement")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;
    private String stripePaymentIntentId;
    private LocalDateTime createdAt;

    @ManyToOne
    private Order order;

    @Enumerated(EnumType.STRING)
    private StatusPaiement status;
}

package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Voucher")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Voucher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVoucher")
    private Long idVoucher;
    private String code;
    private int reduction;
    @Temporal(TemporalType.DATE)
    Date dateExpiration;
    @Enumerated(EnumType.STRING)
    private StatutVoucher statutVoucher;

    @ManyToOne
    Agriculteur agriculteur;

}

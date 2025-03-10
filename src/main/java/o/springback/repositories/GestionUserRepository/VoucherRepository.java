package o.springback.repositories.GestionUserRepository;
import o.springback.entities.GestionUser.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>{
}

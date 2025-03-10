package o.springback.Interfaces.GestionUser;
import o.springback.entities.GestionUser.Voucher;
import java.util.List;

public interface IVoucherService {
    List<Voucher> findAll();
    Voucher findById(Long id);
    Voucher save(Voucher voucher);
    void delete(Long id);
    Voucher update(Voucher voucher);
}

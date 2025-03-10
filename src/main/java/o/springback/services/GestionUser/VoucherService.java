package o.springback.services.GestionUser;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import o.springback.entities.GestionUser.Voucher;
import o.springback.repositories.GestionUserRepository.VoucherRepository;
import o.springback.Interfaces.GestionUser.IVoucherService;
import java.util.List;

@Service
@AllArgsConstructor
public class VoucherService implements IVoucherService{
    private VoucherRepository voucherRepository;
    @Override
    public Voucher save (Voucher voucher){
        return voucherRepository.save(voucher);
    }
    @Override
    public Voucher update(Voucher voucher){
        return voucherRepository.save(voucher);
    }
    @Override
    public void delete(Long id){
        voucherRepository.deleteById(id);
    }
    @Override
    public Voucher findById(Long id){
        return voucherRepository.findById(id).orElse(null);
    }
    @Override
    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }
}

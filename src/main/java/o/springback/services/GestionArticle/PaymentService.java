package o.springback.services.GestionArticle;

import o.springback.Interfaces.GestionArticle.IPaymentService;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Payment;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Long idPayment) {
        return paymentRepository.findById(idPayment)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + idPayment));
    }

    @Override
    public Payment save(Payment payment) {
        if (payment.getPaymentType() == Payment.PaymentType.AUCTION) {
            Auction auction = auctionRepository.findById(payment.getReferenceId())
                    .orElseThrow(() -> new RuntimeException("Auction not found with id " + payment.getReferenceId()));
            payment.setAuction(auction);
            auction.setPayment(payment);
        } else if (payment.getPaymentType() == Payment.PaymentType.RESERVATION) {
            Reservation reservation = reservationRepository.findById(payment.getReferenceId())
                    .orElseThrow(() -> new RuntimeException("Reservation not found with id " + payment.getReferenceId()));
            payment.setReservation(reservation);
            reservation.setPayment(payment);
        } else {
            throw new RuntimeException("Unsupported payment type");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        Payment existing = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + payment.getId()));

        existing.setAmount(payment.getAmount());
        existing.setStatus(payment.getStatus());
        existing.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(existing);
    }

    @Override
    public void delete(Long idPayment) {
        if (!paymentRepository.existsById(idPayment)) {
            throw new RuntimeException("Payment not found with id " + idPayment);
        }
        paymentRepository.deleteById(idPayment);
    }
}


package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Transactional
public class PaymentService implements IPaymentService {

    private PaymentRepository paymentRepository;
    private AuctionRepository auctionRepository;
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

        boolean referenceChanged = !existing.getReferenceId().equals(payment.getReferenceId());

        if (referenceChanged) {
            if (existing.getPaymentType() == Payment.PaymentType.RESERVATION) {
                Reservation oldReservation = reservationRepository.findById(existing.getReferenceId())
                        .orElseThrow(() -> new RuntimeException("Old reservation not found with id " + existing.getReferenceId()));
                oldReservation.setPayment(null);
                reservationRepository.save(oldReservation);


                Reservation newReservation = reservationRepository.findById(payment.getReferenceId())
                        .orElseThrow(() -> new RuntimeException("New reservation not found with id " + payment.getReferenceId()));
                payment.setReservation(newReservation);
                newReservation.setPayment(payment);
                reservationRepository.save(newReservation);
                existing.setReservation(newReservation);
            }

            if (existing.getPaymentType() == Payment.PaymentType.AUCTION) {
                Auction oldAuction = auctionRepository.findById(existing.getReferenceId())
                        .orElseThrow(() -> new RuntimeException("Old auction not found with id " + existing.getReferenceId()));
                oldAuction.setPayment(null);
                auctionRepository.save(oldAuction);

                Auction newAuction = auctionRepository.findById(payment.getReferenceId())
                        .orElseThrow(() -> new RuntimeException("New auction not found with id " + payment.getReferenceId()));
                payment.setAuction(newAuction);
                newAuction.setPayment(payment);
                auctionRepository.save(newAuction);
                existing.setAuction(newAuction);
            }

            existing.setReferenceId(payment.getReferenceId());
        }

        // Update other fields
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


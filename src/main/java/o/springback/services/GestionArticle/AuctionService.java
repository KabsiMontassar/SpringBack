package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IAuctionService;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Bid;
import o.springback.entities.GestionArticle.Payment;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.BidRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService implements IAuctionService {

    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;
    private PaymentRepository paymentRepository;

    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction findById(Long idAuction) {
        return auctionRepository.findById(idAuction)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + idAuction));
    }

    @Override
    public Auction save(Auction auction) {
        auction.setStartTime(LocalDateTime.now());
        auction.setCurrentPrice(auction.getStartPrice());
        auction.setActive(true);

        // Lier les enchères
        if (auction.getBids() != null) {
            for (Bid bid : auction.getBids()) {
                bid.setAuction(auction);
            }
        }

        // Lier le paiement s'il existe déjà (rare à la création)
        if (auction.getPayment() != null) {
            auction.getPayment().setAuction(auction);
            paymentRepository.save(auction.getPayment());
        }

        return auctionRepository.save(auction);
    }

    @Override
    public Auction update(Auction auction) {
        Auction existing = findById(auction.getId());

        existing.setStartPrice(auction.getStartPrice());
        existing.setCurrentPrice(auction.getCurrentPrice());
        existing.setStartTime(auction.getStartTime());
        existing.setEndTime(auction.getEndTime());
        existing.setActive(auction.isActive());

        if (auction.getBids() != null) {
            for (Bid bid : auction.getBids()) {
                bid.setAuction(existing);
            }
            bidRepository.saveAll(auction.getBids());
            existing.setBids(auction.getBids());
        }

        if (auction.getPayment() != null) {
            auction.getPayment().setAuction(existing);
            paymentRepository.save(auction.getPayment());
            existing.setPayment(auction.getPayment());
        }

        return auctionRepository.save(existing);
    }

    @Override
    public void delete(Long idAuction) {
        Auction auction = findById(idAuction);

        if (auction.getBids() != null) {
            bidRepository.deleteAll(auction.getBids());
        }

        if (auction.getPayment() != null) {
            paymentRepository.delete(auction.getPayment());
        }

        auctionRepository.delete(auction);
    }
    @Scheduled(fixedRate = 50000)
    public void deleteExpiredUnpaidAuctions() {
        List<Auction> expiredUnpaidAuctions = auctionRepository.findByIsActiveFalseAndPaymentStatusNotAndEndTimeBefore(
               Payment.Status.PENDING, LocalDateTime.now().minusDays(7));

        if (!expiredUnpaidAuctions.isEmpty()) {
            auctionRepository.deleteAll(expiredUnpaidAuctions);
            System.out.println("Enchères expirées et non payées supprimées");
        } else {
            System.out.println("Aucune enchère expirée non payée à supprimer");
        }
    }

    @Scheduled(fixedRate = 50000)
    public void processAuctionWinners() {
        List<Auction> endedAuctions = auctionRepository.findByIsActiveFalseAndPaymentIsNull();

        if (!endedAuctions.isEmpty()) {
            for (Auction auction : endedAuctions) {
                List<Bid> bids = auction.getBids();
                if (!bids.isEmpty()) {
                    Bid winningBid = bids.stream()
                            .max(Comparator.comparing(Bid::getBidAmount))
                            .orElse(null);

                    Payment payment = new Payment();
                    payment.setAmount(winningBid.getBidAmount());
                    payment.setPaymentDate(LocalDateTime.now());
                    payment.setPaymentType(Payment.PaymentType.AUCTION);
                    payment.setStatus(Payment.Status.PENDING);
                    payment.setReferenceId(auction.getId());

                    paymentRepository.save(payment);

                    auction.setPayment(payment);
                    auctionRepository.save(auction);
                    System.out.println("Paiement créé pour l'enchère ID: " + auction.getId());
                }
            }
        } else {
            System.out.println("Aucune enchère terminée à traiter");
        }
    }
    public List<Auction> getTop5AuctionsByBids() {
        return auctionRepository.findTop5ByBidsCount();
    }


}

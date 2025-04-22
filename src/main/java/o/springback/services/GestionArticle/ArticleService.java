package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Payment;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class ArticleService implements IArticleService {
    private ArticleRepository articleRepository;
    private AuctionRepository auctionRepository;
    private ReservationRepository reservationRepository;

    @Override
    public List<Article> findAll() {
        List<Article> articles = articleRepository.findAll();

        for (Article article : articles) {
            boolean isAvailable = true;

            switch (article.getTypeArticle()) {
                case AUCTION:
                    isAvailable = article.getAuction() == null;
                    break;

                case RESERVATION:
                    isAvailable = article.getReservations() == null || article.getReservations().isEmpty();
                    break;
            }

            article.setAvailable(isAvailable);
        }

        return articles;
    }

    @Override
    public Article findById(Long idArticle) {
        return articleRepository.findById(idArticle)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + idArticle));
    }

    @Override
    public Article save(Article article) {
        // Créer un nouvel article pour éviter les entités détachées
        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setDescription(article.getDescription());
        newArticle.setImageUrl(article.getImageUrl());
        newArticle.setPricePerHour(article.getPricePerHour());
        newArticle.setPrix(article.getPrix());
        newArticle.setTypeArticle(article.getTypeArticle());
        newArticle.setAvailable(true); // par défaut
        newArticle.setCreatedAt(LocalDateTime.now());

        switch (article.getTypeArticle()) {
            case AUCTION:
                if (article.getReservations() != null && !article.getReservations().isEmpty()) {
                    throw new IllegalArgumentException("An article of type AUCTION cannot have reservations.");
                }

                if (article.getAuction() != null) {
                    Auction auction = article.getAuction();
                    auction.setStartPrice(article.getPrix());
                    auction.setCurrentPrice(article.getPrix());
                    auction.setArticle(newArticle);
                    newArticle.setAuction(auction);
                    newArticle.setPricePerHour(0.0f); // désactiver prix/h pour enchères
                    newArticle.setAvailable(false);
                }
                break;

            case RESERVATION:
                if (article.getAuction() != null) {
                    throw new IllegalArgumentException("An article of type RESERVATION cannot have an auction.");
                }

                if (article.getPricePerHour() == null || article.getPricePerHour() <= 0) {
                    throw new IllegalArgumentException("Price per hour must be set for RESERVATION type.");
                }

                if (article.getReservations() != null && !article.getReservations().isEmpty()) {
                    List<Reservation> newReservations = new ArrayList<>();
                    for (Reservation reservation : article.getReservations()) {
                        long hours = Duration.between(reservation.getStartDatetime(), reservation.getEndDatetime()).toHours();
                        if (hours <= 0) {
                            throw new IllegalArgumentException("End datetime must be after start datetime.");
                        }
                        reservation.setTotalPrice(hours * article.getPricePerHour());
                        reservation.setArticle(newArticle);
                        newReservations.add(reservation);
                    }
                    newArticle.setReservations(newReservations);
                    newArticle.setPrix(0.0f); // désactiver prix fixe pour les réservations
                    newArticle.setAvailable(false);
                }
                break;
        }

        return articleRepository.save(newArticle);
    }

    @Override
    public Article update(Article updatedArticle) {
        // Reload the existing article from the database
        Article existing = findById(updatedArticle.getId());

        // Update scalar fields
        existing.setTitle(updatedArticle.getTitle());
        existing.setDescription(updatedArticle.getDescription());
        existing.setImageUrl(updatedArticle.getImageUrl());
        existing.setTypeArticle(updatedArticle.getTypeArticle());
        existing.setAvailable(updatedArticle.isAvailable());

        // Validate prix based on the article type
        if (updatedArticle.getTypeArticle() == Payment.PaymentType.AUCTION && updatedArticle.getPrix() <= 0) {
            throw new IllegalArgumentException("The prix must be greater than 0 for AUCTION type.");
        }

        // Handle logic based on the article type
        switch (updatedArticle.getTypeArticle()) {
            case AUCTION:
                // Ensure an auction-type article does not have reservations
                if (updatedArticle.getReservations() != null && !updatedArticle.getReservations().isEmpty()) {
                    throw new IllegalArgumentException("An article of type AUCTION cannot have reservations.");
                }

                // Update the prix field directly
                existing.setPrix(updatedArticle.getPrix());

                // Handle auction-specific logic if an auction object is provided
                if (updatedArticle.getAuction() != null) {
                    Auction auction = updatedArticle.getAuction();
                    auction.setStartPrice(updatedArticle.getPrix());
                    auction.setCurrentPrice(updatedArticle.getPrix());
                    auction.setArticle(existing);

                    // Save the auction
                    auctionRepository.save(auction);

                    // Update auction and disable price per hour
                    existing.setAuction(auction);
                } else {
                    existing.setAuction(null); // Clear auction if not provided
                }

                // Disable price per hour for auction type
                existing.setPricePerHour(0.0f);
                break;

            case RESERVATION:
                // Ensure a reservation-type article does not have an auction
                if (updatedArticle.getAuction() != null) {
                    throw new IllegalArgumentException("An article of type RESERVATION cannot have an auction.");
                }

                // Handle reservations safely
                if (updatedArticle.getReservations() != null) {
                    existing.getReservations().clear(); // Clear existing reservations

                    for (Reservation reservation : updatedArticle.getReservations()) {
                        reservation.setArticle(existing);
                        existing.getReservations().add(reservation); // Add to the existing collection
                    }

                    // Save all reservations for the article
                    reservationRepository.saveAll(existing.getReservations());
                } else {
                    existing.getReservations().clear(); // Clear reservations if not provided
                }

                // Disable prix for reservation-type articles
                existing.setPrix(0.0f);

                // Validate price per hour for RESERVATION type
                if (updatedArticle.getPricePerHour() == null || updatedArticle.getPricePerHour() <= 0) {
                    throw new IllegalArgumentException("Price per hour must be set for RESERVATION type.");
                }

                // Update price per hour for reservations
                existing.setPricePerHour(updatedArticle.getPricePerHour());
                break;
        }

        // Save the updated article
        return articleRepository.save(existing);
    }

    @Override
    public void delete(Long idArticle) {
        Article article = findById(idArticle);

        if (article.getReservations() != null) {
            reservationRepository.deleteAll(article.getReservations());
        }

        if (article.getAuction() != null) {
            auctionRepository.delete(article.getAuction());
        }

        articleRepository.delete(article);
    }

    @Override
    public List<Article> findByTypeArticle(Payment.PaymentType typeArticle) {
        return articleRepository.findByTypeArticle(typeArticle);
    }





  @Override
  public void AffectAuctionToArticle(Long idArticle, Long idAuction) {
        Article article = findById(idArticle);
        Auction auction = auctionRepository.findById(idAuction)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + idAuction));

        article.setAuction(auction);
        article.setAvailable(false);
        articleRepository.save(article);
    }



















}

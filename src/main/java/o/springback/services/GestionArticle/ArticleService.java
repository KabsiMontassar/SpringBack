package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.PaymentArticle;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ArticleService implements IArticleService {
    private ArticleRepository articleRepository;
    private AuctionRepository auctionRepository;
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;


    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


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
        if(article.getUser() == null || article.getUser().getIdUser() == null) {
            throw new IllegalArgumentException("User is required for a reservation.");
        }
        if (articleRepository.existsByTitle(article.getTitle())) {
            throw new IllegalArgumentException("An article with the title '" + article.getTitle() + "' already exists.");
        }

        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setDescription(article.getDescription());
        newArticle.setImageUrl(article.getImageUrl());
        newArticle.setPricePerHour(article.getPricePerHour());
        newArticle.setPrix(article.getPrix());
        newArticle.setTypeArticle(article.getTypeArticle());
        newArticle.setAvailable(true);
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
                    newArticle.setPricePerHour(0.0f);
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
                    newArticle.setPrix(0.0f);
                    newArticle.setAvailable(false);
                }
                break;
        }

        return articleRepository.save(newArticle);
    }

    @Override
    public Article update(Article updatedArticle) {
        Article existing = findById(updatedArticle.getId());

        if (!existing.getTitle().equals(updatedArticle.getTitle()) && articleRepository.existsByTitle(updatedArticle.getTitle())) {
            throw new IllegalArgumentException("An article with the title '" + updatedArticle.getTitle() + "' already exists.");
        }

        existing.setTitle(updatedArticle.getTitle());
        existing.setDescription(updatedArticle.getDescription());
        existing.setImageUrl(updatedArticle.getImageUrl());
        existing.setTypeArticle(updatedArticle.getTypeArticle());
        existing.setAvailable(updatedArticle.isAvailable());

        if (updatedArticle.getTypeArticle() == PaymentArticle.PaymentType.AUCTION && updatedArticle.getPrix() <= 0) {
            throw new IllegalArgumentException("The prix must be greater than 0 for AUCTION type.");
        }

        switch (updatedArticle.getTypeArticle()) {
            case AUCTION:
                if (updatedArticle.getReservations() != null && !updatedArticle.getReservations().isEmpty()) {
                    throw new IllegalArgumentException("An article of type AUCTION cannot have reservations.");
                }

                existing.setPrix(updatedArticle.getPrix());

                if (updatedArticle.getAuction() != null) {
                    Auction auction = updatedArticle.getAuction();
                    auction.setStartPrice(updatedArticle.getPrix());
                    auction.setCurrentPrice(updatedArticle.getPrix());
                    auction.setArticle(existing);

                    auctionRepository.save(auction);


                    existing.setAuction(auction);
                } else {
                    existing.setAuction(null);
                }

                existing.setPricePerHour(0.0f);
                break;

            case RESERVATION:
                if (updatedArticle.getAuction() != null) {
                    throw new IllegalArgumentException("An article of type RESERVATION cannot have an auction.");
                }

                if (updatedArticle.getReservations() != null) {
                    existing.getReservations().clear();

                    for (Reservation reservation : updatedArticle.getReservations()) {
                        reservation.setArticle(existing);
                        existing.getReservations().add(reservation);
                    }


                    reservationRepository.saveAll(existing.getReservations());
                } else {
                    existing.getReservations().clear();
                }

                existing.setPrix(0.0f);

                if (updatedArticle.getPricePerHour() == null || updatedArticle.getPricePerHour() <= 0) {
                    throw new IllegalArgumentException("Price per hour must be set for RESERVATION type.");
                }

                existing.setPricePerHour(updatedArticle.getPricePerHour());
                break;
        }

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
    public List<Article> findByTypeArticle(PaymentArticle.PaymentType typeArticle) {
        return articleRepository.findByTypeArticle(typeArticle);
    }


    public Article getArticleByTitle(String title) {
        return articleRepository.findByTitle(title);}




    @Override
    public void AffectAuctionToArticle(Long idArticle, Long idAuction) {
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + idArticle));

        Auction auction = auctionRepository.findById(idAuction)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + idAuction));

        if (!article.isAvailable()) {
            throw new IllegalArgumentException("Article is not available for auction.");
        }

        article.setAuction(auction);
        article.setAvailable(false);
        articleRepository.save(article);
    }

    @Override
    public List<Article> searchByTitle(String title) {
        return articleRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Article> findByIsAvailableAndTypeArticle(boolean isAvailable, PaymentArticle.PaymentType typeArticle) {
        return articleRepository.findByIsAvailableAndTypeArticle(isAvailable, typeArticle);
    }

    /*
@Override
    public List<Map<String, Object>> getUsersWithScores() {
        List<Object[]> results = articleRepository.findUsersWithArticleScores();
        List<Map<String, Object>> usersWithScores = new ArrayList<>();

        for (Object[] result : results) {
            User user = (User) result[0];
            Long score = ((Number) result[1]).longValue();

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user", user);
            userMap.put("score", score);

            usersWithScores.add(userMap);
        }

        return usersWithScores;
    }

@Override
    public List<Map<String, Object>> getArticlesOrderedByUserScore() {
        List<Object[]> results = articleRepository.findArticlesOrderedByUserScore();
        List<Map<String, Object>> articlesWithScores = new ArrayList<>();

        for (Object[] result : results) {
            Article article = (Article) result[0];
            Long userScore = ((Number) result[1]).longValue();

            Map<String, Object> articleMap = new HashMap<>();
            articleMap.put("article", article);
            articleMap.put("userScore", userScore);

            articlesWithScores.add(articleMap);
        }

        return articlesWithScores;
    }

@Override
    public Map<User, List<Article>> getArticlesGroupedByUserScore() {
        List<Map<String, Object>> usersWithScores = getUsersWithScores();
        Map<User, List<Article>> result = new LinkedHashMap<>();

        for (Map<String, Object> userWithScore : usersWithScores) {
            User user = (User) userWithScore.get("user");
            // Récupère tous les articles de l'utilisateur
            List<Article> userArticles = user.getArticles().stream()
                    .sorted(Comparator.comparing(Article::getCreatedAt).reversed())
                    .collect(Collectors.toList());

            result.put(user, userArticles);
        }

        return result;
    }*/
















}

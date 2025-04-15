package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long idArticle) {
        return articleRepository.findById(idArticle)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + idArticle));
    }

    public Article save(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setAvailable(true);

        if (article.getAuction() != null) {
            Auction auction = article.getAuction();
            auction.setArticle(article);
            auctionRepository.save(auction);
        }

        if (article.getReservations() != null) {
            for (Reservation reservation : article.getReservations()) {
                reservation.setArticle(article);
            }
        }

        return articleRepository.save(article);
    }

    public Article update(Article article) {
        Article existing = findById(article.getId());

        existing.setTitle(article.getTitle());
        existing.setDescription(article.getDescription());
        existing.setImageUrl(article.getImageUrl());
        existing.setPricePerHour(article.getPricePerHour());
        existing.setAvailable(article.isAvailable());

        if (article.getAuction() != null) {
            Auction auction = article.getAuction();
            auction.setArticle(existing);
            auctionRepository.save(auction);
            existing.setAuction(auction);
        }

        if (article.getReservations() != null) {
            for (Reservation reservation : article.getReservations()) {
                reservation.setArticle(existing);
            }
            reservationRepository.saveAll(article.getReservations());
            existing.setReservations(article.getReservations());
        }

        return articleRepository.save(existing);
    }

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
}

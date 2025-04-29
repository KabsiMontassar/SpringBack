package o.springback.controller.GestionArticle;
import com.google.api.client.util.Lists;
import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.PaymentArticle;
import o.springback.entities.GestionUser.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article saved = articleService.save(article);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        Article updated = articleService.update(article);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Article>> getArticlesByType(@PathVariable PaymentArticle.PaymentType type) {
        return ResponseEntity.ok(articleService.findByTypeArticle(type));
    }

    @GetMapping("/articlesearch")
    public List<Article> searchArticle(@RequestParam String title) {
        return articleService.searchByTitle(title);
    }

    @GetMapping("/by-availability-and-type")
    public ResponseEntity<List<Article>> getArticlesByAvailabilityAndType(
            @RequestParam boolean available,
            @RequestParam PaymentArticle.PaymentType type) {
        return ResponseEntity.ok(articleService.findByIsAvailableAndTypeArticle(available, type));
    }


    /*
    @GetMapping("/users-with-scores")
    public ResponseEntity<List<Map<String, Object>>> getUsersWithScores() {
        List<Map<String, Object>> usersWithScores = Lists.newArrayList(articleService.getUsersWithScores());
        return new ResponseEntity<>(usersWithScores, HttpStatus.OK);
    }

    /**
     * Récupère les articles triés par le score de leur utilisateur
     * @return Liste des articles avec le score de leur utilisateur

    @GetMapping("/articles-by-user-score")
    public ResponseEntity<List<Map<String, Object>>> getArticlesOrderedByUserScore() {
        List<Map<String, Object>> articlesWithScores = articleService.getArticlesOrderedByUserScore();
        return new ResponseEntity<>(articlesWithScores, HttpStatus.OK);
    }

    /**
     * Récupère les articles regroupés par utilisateur et triés par score
     * @return Map des utilisateurs avec leurs articles

    @GetMapping("/articles-grouped-by-user")
    public ResponseEntity<Map<String, Object>> getArticlesGroupedByUser() {
        Map<User, List<Article>> groupedArticles = articleService.getArticlesGroupedByUserScore();

        // Transformer le résultat pour la sérialisation JSON
        Map<String, Object> response = new HashMap<>();
        int index = 0;

        for (Map.Entry<User, List<Article>> entry : groupedArticles.entrySet()) {
            User user = entry.getKey();
            List<Article> articles = entry.getValue();

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userData", user);
            userInfo.put("articles", articles);

            // Ajout du score dans la réponse
            for (Map<String, Object> userWithScore : articleService.getUsersWithScores()) {
                if (userWithScore.get("user").equals(user)) {
                    userInfo.put("score", userWithScore.get("score"));
                    break;
                }
            }

            // Utiliser l'index comme clé pour garantir l'ordre
            response.put("rank_" + (++index), userInfo);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
*/

}


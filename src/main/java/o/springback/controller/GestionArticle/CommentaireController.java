package o.springback.controller.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.ICommentaireService;
import o.springback.entities.GestionArticle.Commentaire;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commentaire")
public class CommentaireController {
    private final ICommentaireService commentaireService;

    @GetMapping("/allCommentaire")
    public List<Commentaire> getAllCommentaire() {
        return commentaireService.findAll();
    }

    @GetMapping("/{idCommentaire}")
    public Commentaire getCommentaireById(@PathVariable Long idCommentaire) {
        return commentaireService.findById(idCommentaire);
    }

    @PostMapping("/addCommentaire")
    public Commentaire addCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.save(commentaire);
    }

    @PutMapping("/updateCommentaire")
    public Commentaire updateCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.update(commentaire);
    }


    @DeleteMapping("/deleteCommentaire/{idCommentaire}")
    public void deleteCommentaire(@PathVariable Long idCommentaire) {
        commentaireService.delete(idCommentaire);
    }
}
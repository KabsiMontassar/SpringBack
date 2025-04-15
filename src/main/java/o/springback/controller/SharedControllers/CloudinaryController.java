package o.springback.controller.SharedControllers;


import o.springback.entities.Shared.Image;
import o.springback.services.Shared.CloudinaryService;
import o.springback.services.Shared.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {


    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile mfile) throws IOException {
        BufferedImage bi = ImageIO.read(mfile.getInputStream());
        if (bi == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image file");
        }
        Map Result = cloudinaryService.upload(mfile);
        Image image = new Image(
                (Long) Result.get("original_filename"),
                (String) Result.get("public_id"),
                (String) Result.get("url"),
                (String) Result.get("secure_url")
        );
        imageService.saveImage(image);
        return ResponseEntity.ok("Image uploaded successfully");

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) throws IOException {

        Optional<Image> imageOptional = imageService.getOne(id);
         if(imageOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
         }
        Image image = imageOptional.get();
         String CloudinaryImageId = image.getImageId();
         try{
            cloudinaryService.delete(Long.parseLong(CloudinaryImageId));
            imageService.deleteImage(id);
            return ResponseEntity.ok("Image deleted successfully");
         }
            catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image from Cloudinary");
            }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.getOne(id);
        return imageOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}

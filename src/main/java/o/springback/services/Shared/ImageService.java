package o.springback.services.Shared;

import jakarta.transaction.Transactional;
import o.springback.entities.Shared.Image;
import o.springback.repositories.Shared.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    public List<Image> getAllImages() {
        return imageRepository.findByOrderById();
    }

    public Optional<Image> getOne(Long id) {
        return imageRepository.findById(id);
    }

    public Image saveImage(Image image, MultipartFile file) throws IOException {
        String imageUrl = firebaseStorageService.uploadFile(file);
        image.setImageUrl(imageUrl);
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            firebaseStorageService.deleteFile(image.getImageUrl());
            imageRepository.deleteById(id);
        }
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }

    public boolean existsByImageUrl(String url) {
        return imageRepository.existsByImageUrl(url);
    }

    public long FindOneImageByUrl(String url) {
        Image image = imageRepository.findByImageUrl(url);
        if (image != null) {
            return image.getId();
        } else {
            return -1; // or throw an exception
        }

    }
}

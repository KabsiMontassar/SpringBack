package o.springback.services.Shared;


import jakarta.transaction.Transactional;
import o.springback.entities.Shared.Image;
import o.springback.repositories.Shared.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findByOrderById();
    }

    public Optional<Image> getOne(Long id) {
        return imageRepository.findById(id);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }
}

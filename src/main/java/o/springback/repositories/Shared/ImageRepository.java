package o.springback.repositories.Shared;


import o.springback.entities.Shared.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    List<Image> findByOrderById();
}

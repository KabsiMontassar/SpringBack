package o.springback.services.Shared;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    Cloudinary cloudinary ;


    public CloudinaryService() {
        Map<String, Object> config = new HashMap<String, Object>();
        config.put("cloud_name", "dxsf9g6l3");
        config.put("api_key", "921759666974192");
        config.put("api_secret", "7vNbl27FjzZ33GWR1cwzEt_DZe0");
        cloudinary = new Cloudinary(config);

    }


    public Map upload(MultipartFile mfile) throws IOException {
        File file = convert(mfile);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if(!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return uploadResult;
    }


    public Map delete(long id) throws IOException {
        String idString = String.valueOf(id);
        return cloudinary.uploader().destroy(idString, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile mfile) throws IOException {
        File file =  new File(Objects.requireNonNull(mfile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());
        fos.close();
        return file;
    }



}

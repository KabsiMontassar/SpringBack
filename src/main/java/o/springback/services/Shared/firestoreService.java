package o.springback.services.Shared;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class firestoreService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private FirebaseApp firebaseApp;

    private static final String BUCKET_NAME = "divine-data-421013.appspot.com";

    private Storage getStorage() {
        return StorageOptions.newBuilder()
                .setProjectId("divine-data-421013")
                .build()
                .getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        Storage storage = getStorage();
        Blob blob = storage.create(blobInfo, file.getBytes());

        return blob.getMediaLink();
    }

    public void deleteFile(String fileName) {
        Storage storage = getStorage();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        storage.delete(blobId);
    }

    public List<Map<String, String>> listFiles() {
        Storage storage = getStorage();
        Bucket bucket = storage.get(BUCKET_NAME);

        List<Map<String, String>> fileList = new ArrayList<>();
        bucket.list().iterateAll().forEach(blob -> {
            Map<String, String> fileInfo = new HashMap<>();
            fileInfo.put("name", blob.getName());
            fileInfo.put("url", blob.getMediaLink());
            fileInfo.put("contentType", blob.getContentType());
            fileInfo.put("size", String.valueOf(blob.getSize()));
            fileList.add(fileInfo);
        });

        return fileList;
    }

    public Map<String, String> getFileDetails(String fileName) {
        Storage storage = getStorage();
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, fileName));

        if (blob == null) {
            return null;
        }

        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put("name", blob.getName());
        fileInfo.put("url", blob.getMediaLink());
        fileInfo.put("contentType", blob.getContentType());
        fileInfo.put("size", String.valueOf(blob.getSize()));
        return fileInfo;
    }
}

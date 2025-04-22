package o.springback.services.Shared;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String fileUrl);
    Blob getFileMetadata(String fileName);
    Page<Blob> listFiles(String prefix, String pageToken, int pageSize);
    String generateV4GetObjectSignedUrl(String objectName) throws IOException;
    boolean doesFileExist(String fileName);
    byte[] downloadFile(String fileName) throws IOException;
}

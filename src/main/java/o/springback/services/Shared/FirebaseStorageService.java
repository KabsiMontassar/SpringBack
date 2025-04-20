package o.springback.services.Shared;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.google.api.gax.paging.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageService implements StorageService {
    @Autowired
    private Storage storage;

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return fileName;
    }

    @Override
    public void deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }

    @Override
    public Blob getFileMetadata(String fileName) {
        return storage.get(BlobId.of(bucketName, fileName));
    }

    @Override
    public Page<Blob> listFiles(String prefix, String pageToken, int pageSize) {
        return storage.list(bucketName,
                Storage.BlobListOption.prefix(prefix),
                Storage.BlobListOption.pageToken(pageToken),
                Storage.BlobListOption.pageSize(pageSize));
    }

    @Override
    public String generateV4GetObjectSignedUrl(String objectName) throws IOException {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
        return storage.signUrl(blobInfo, 15, TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature()).toString();
    }

    @Override
    public boolean doesFileExist(String fileName) {
        return storage.get(BlobId.of(bucketName, fileName)) != null;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob == null) throw new IOException("File not found");
        return blob.getContent();
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }
}

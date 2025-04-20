package o.springback.controller.SharedControllers;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import o.springback.services.Shared.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/firebase")
@CrossOrigin(origins = "*")
public class FirebaseStorageController {

    @Autowired
    private FirebaseStorageService storageService;

    

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String imageUrl = storageService.uploadFile(file);
            String url = storageService.generateV4GetObjectSignedUrl(imageUrl);
            
            Map<String, String> response = new HashMap<>();
            response.put("id", "1");
            response.put("fileName", imageUrl);
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{imageUrl}")
    public ResponseEntity<Void> deleteFile(@PathVariable String imageUrl) {

        storageService.deleteFile(imageUrl);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] data = storageService.downloadFile(fileName);
            Blob metadata = storageService.getFileMetadata(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(metadata.getContentType()))
                    .contentLength(data.length)
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Blob>> listFiles(
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String pageToken,
            @RequestParam(defaultValue = "100") int pageSize) {
        return ResponseEntity.ok(storageService.listFiles(prefix, pageToken, pageSize));
    }

    @GetMapping("/url/{fileName}")
    public ResponseEntity<String> generateSignedUrl(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok(storageService.generateV4GetObjectSignedUrl(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

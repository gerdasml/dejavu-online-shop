package lt.dejavu.storage;

import lt.dejavu.storage.model.ImageFormat;
import lt.dejavu.storage.strategy.StorageStrategy;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${rest.basePath}/image")
public class ImageApi {
    @Autowired
    private StorageStrategy storageStrategy;

    @Autowired
    private List<ImageFormat> allowedFormats;


    @PostMapping("/upload")
    public void uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        ImageFormat format = ImageFormat.resolve(extension);
        if(format == ImageFormat.UNKNOWN) {
            // TODO: proper exception
            throw new RuntimeException("Unknown file format");
        }
        if(!allowedFormats.contains(format)) {
            // TODO: proper exception
            throw new RuntimeException("Unsupported file format");
        }
        byte[] content = file.getBytes();
        storageStrategy.saveFile(content, 10, format);
    }
}

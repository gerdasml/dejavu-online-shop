package lt.dejavu.storage.image;

import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.strategy.ImageStorageStrategy;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${rest.basePath}/image")
public class ImageApi {
    @Autowired
    private ImageStorageStrategy imageStorageStrategy;

    @Autowired
    private List<ImageFormat> allowedFormats;

    @GetMapping("/")
    public List<ImageInfo> getAllImages() {
        throw new NotImplementedException();
    }

    @GetMapping("/{imageId}/info")
    public ImageInfo getImageInfo(@PathVariable("imageId") int id) {
        throw new NotImplementedException();
    }

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
        imageStorageStrategy.saveFile(content, 10, format);
    }
}

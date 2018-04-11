package lt.dejavu.storage.image;

import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.service.ImageStorageService;
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
    private ImageStorageService imageStorageService;

    @Autowired
    private List<ImageFormat> allowedFormats;

    @GetMapping("/")
    public List<ImageInfo> getAllImages() {
        return imageStorageService.getAllImageInfo();
    }

    @GetMapping("/{imageId}/info")
    public ImageInfo getImageInfo(@PathVariable("imageId") long id) {
        return imageStorageService.getImageInfo(id);
    }

    @PostMapping("/upload")
    public ImageInfo uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
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

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setExtension(format.getExtension());
        imageInfo.setFilename(FilenameUtils.getBaseName(file.getOriginalFilename()));
        return imageStorageService.saveImage(file.getBytes(), imageInfo);
    }
}

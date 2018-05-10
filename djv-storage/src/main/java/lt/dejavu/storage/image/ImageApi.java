package lt.dejavu.storage.image;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.storage.image.exception.FileNotFoundException;
import lt.dejavu.storage.image.exception.UnsupportedImageFormatException;
import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.service.ImageStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${rest.image}")
public class ImageApi {
    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private List<ImageFormat> allowedFormats;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public List<ImageInfo> getAllImages() {
        return imageStorageService.getAllImageInfo();
    }

    @GetMapping("/{imageId}/info")
    public ImageInfo getImageInfo(@PathVariable("imageId") long id) throws FileNotFoundException {
        return imageStorageService.getImageInfo(id);
    }

    @PostMapping("/upload")
    public ImageInfo uploadImage(HttpServletRequest request,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                 @RequestParam("file") MultipartFile file) throws IOException, UnsupportedImageFormatException, ApiSecurityException {
        authorize(authHeader, request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        ImageFormat format = ImageFormat.resolve(extension);
        if (format == ImageFormat.UNKNOWN || !allowedFormats.contains(format)) {
            throw new UnsupportedImageFormatException("This image format is not supported");
        }

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setExtension(format.getExtension());
        imageInfo.setFilename(FilenameUtils.getBaseName(file.getOriginalFilename()));
        return imageStorageService.saveImage(file.getBytes(), imageInfo);
    }

    // TODO: move these two methods to some common place
    private Endpoint buildEndpoint(HttpServletRequest request) {
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(RequestMethod.valueOf(request.getMethod()));
        endpoint.setPath(request.getRequestURI());
        return endpoint;
    }

    private void authorize(String authHeader, HttpServletRequest request) throws ApiSecurityException {
        securityService.authorizeEndpoint(authHeader, buildEndpoint(request));
    }
}

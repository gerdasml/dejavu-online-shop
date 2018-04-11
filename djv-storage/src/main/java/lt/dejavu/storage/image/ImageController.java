package lt.dejavu.storage.image;

import lt.dejavu.storage.image.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("${rest.basePath}/image")
public class ImageController {
    @Autowired
    private ImageStorageService imageStorageService;

    @GetMapping(
            path = "/{imageId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    @ResponseBody
    public byte[] getImage(@PathVariable("imageId") long imageId) throws IOException {
        return imageStorageService.getImage(imageId);
    }
}

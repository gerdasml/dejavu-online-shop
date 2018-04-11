package lt.dejavu.storage;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("${rest.basePath}/image")
public class ImageController {
    @GetMapping(
            path = "/{imageId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    @ResponseBody
    public byte[] getImage(@PathVariable("imageId") int imageId) throws IOException {
        // TODO: get image by id
        final String ext = "JPG";
        BufferedImage image = ImageIO.read(new File("C:\\Users\\vstri\\Desktop\\Capture." + ext));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, ext, baos);
        return baos.toByteArray();
    }
}

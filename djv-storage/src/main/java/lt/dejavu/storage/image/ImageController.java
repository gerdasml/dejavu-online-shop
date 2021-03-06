package lt.dejavu.storage.image;

import lt.dejavu.storage.image.exception.FileNotFoundException;
import lt.dejavu.storage.image.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageStorageService imageStorageService;

    @GetMapping(
            path = "/{imageId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("imageId") long imageId) {
        try {
            return new ResponseEntity<>(imageStorageService.getImage(imageId), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package lt.dejavu.storage.image.mapper;

import lt.dejavu.storage.image.helper.ImageUrlBuilder;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.model.db.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageInfoMapper {
    private final ImageUrlBuilder imageUrlBuilder;

    @Autowired
    public ImageInfoMapper(ImageUrlBuilder imageUrlBuilder) {
        this.imageUrlBuilder = imageUrlBuilder;
    }

    public ImageInfo mapFromImage(Image img) {
        ImageInfo info = new ImageInfo();
        info.setExtension(img.getExtension());
        info.setFilename(img.getFilename());
        info.setUploadDateTime(img.getUploadedOn());
        info.setUrl(imageUrlBuilder.buildUrl(img.getId()));

        return info;
    }

    public Image mapToImage(ImageInfo imageInfo) {
        Image image = new Image();
        image.setExtension(imageInfo.getExtension());
        image.setFilename(imageInfo.getFilename());
        image.setUploadedOn(imageInfo.getUploadDateTime());

        return image;
    }
}

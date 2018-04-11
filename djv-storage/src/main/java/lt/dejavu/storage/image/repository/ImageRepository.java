package lt.dejavu.storage.image.repository;

import lt.dejavu.storage.image.model.ImageInfo;

import java.util.List;

public interface ImageRepository {
    List<ImageInfo> getAllImageInfo();

    ImageInfo getImageInfo(int id);

    int saveImageInfo(ImageInfo imageInfo);
}

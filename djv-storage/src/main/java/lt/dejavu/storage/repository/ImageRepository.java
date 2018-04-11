package lt.dejavu.storage.repository;

import lt.dejavu.storage.model.ImageInfo;

import java.util.List;

public interface ImageRepository {
    List<ImageInfo> getAllImageInfo();

    ImageInfo getImageInfo(int id);

    int saveImageInfo(ImageInfo imageInfo);
}

package lt.dejavu.storage.image.service;

import lt.dejavu.storage.image.model.ImageInfo;

import java.io.IOException;
import java.util.List;

public interface ImageStorageService {
    List<ImageInfo> getAllImageInfo();

    ImageInfo getImageInfo(long id);

    ImageInfo saveImage(byte[] contents, ImageInfo info) throws IOException;

    byte[] getImage(long id) throws IOException;
}

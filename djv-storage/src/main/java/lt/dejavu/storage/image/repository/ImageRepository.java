package lt.dejavu.storage.image.repository;

import lt.dejavu.storage.image.model.db.Image;

import java.util.List;

public interface ImageRepository {
    List<Image> getImages();

    Image getImage(int id);

    long saveImage(Image image);
}

package lt.dejavu.storage.image.service;

import lt.dejavu.storage.image.helper.ImageUrlBuilder;
import lt.dejavu.storage.image.mapper.ImageInfoMapper;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.model.db.Image;
import lt.dejavu.storage.image.repository.ImageRepository;
import lt.dejavu.storage.image.strategy.ImageStorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ImageStorageServiceImpl implements ImageStorageService {
    private final ImageRepository imageRepository;
    private final ImageStorageStrategy imageStorageStrategy;
    private final ImageInfoMapper imageInfoMapper;
    private final ImageUrlBuilder imageUrlBuilder;

    @Autowired
    public ImageStorageServiceImpl(ImageRepository imageRepository, ImageStorageStrategy imageStorageStrategy, ImageInfoMapper imageInfoMapper, ImageUrlBuilder imageUrlBuilder) {
        this.imageRepository = imageRepository;
        this.imageStorageStrategy = imageStorageStrategy;
        this.imageInfoMapper = imageInfoMapper;
        this.imageUrlBuilder = imageUrlBuilder;
    }

    @Override
    public List<ImageInfo> getAllImageInfo() {
        return imageRepository.getImages()
                              .stream()
                              .map(imageInfoMapper::mapFromImage)
                              .collect(toList());
    }

    @Override
    public ImageInfo getImageInfo(long id) {
        return imageInfoMapper.mapFromImage(imageRepository.getImage(id));
    }

    @Override
    public ImageInfo saveImage(byte[] contents, ImageInfo info) {
        info.setUploadDateTime(Timestamp.from(Instant.now()));
        Image image = imageInfoMapper.mapToImage(info);
        long id = imageRepository.saveImage(image);
        imageStorageStrategy.saveFile(contents, id, info.getExtension());
        info.setUrl(imageUrlBuilder.buildUrl(id));
        return info;
    }

    @Override
    public byte[] getImage(long id) {
        return imageStorageStrategy.getFile(id);
    }
}

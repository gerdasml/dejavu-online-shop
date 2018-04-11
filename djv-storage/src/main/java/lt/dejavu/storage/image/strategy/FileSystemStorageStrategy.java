package lt.dejavu.storage.image.strategy;

import lt.dejavu.storage.image.exception.FileNotFoundException;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class FileSystemStorageStrategy implements StorageStrategy {

    private final String basePath;

    public FileSystemStorageStrategy(String basePath) {
        this.basePath = basePath;
        File dir = new File(basePath);
        dir.mkdirs();
    }

    @Override
    public void saveFile(byte[] contents, long id, String extension) throws IOException {
        final String fileName = id + "." + extension;
        Path path = Paths.get(basePath, fileName);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toString()));
        out.write(contents);
    }

    @Override
    public byte[] getFile(long id) throws IOException, FileNotFoundException {
        File file = Arrays.stream(Objects.requireNonNull(new File(basePath).listFiles()))
                          .filter(f -> FilenameUtils.removeExtension(f.getName()).equals(String.valueOf(id)))
                          .findFirst()
                          .orElse(null);
        if (file == null) {
            throw new FileNotFoundException("The file with the specified id was not found");
        }
        return Files.readAllBytes(file.toPath());
    }
}

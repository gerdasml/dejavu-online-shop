package lt.dejavu.storage.image.strategy;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class FileSystemImageStorageStrategy implements ImageStorageStrategy {

    private final String basePath;

    public FileSystemImageStorageStrategy(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveFile(byte[] contents, long id, String extension) {
        final String fileName = id + "." + extension;
        Path path = Paths.get(basePath, fileName);
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toString()));
            out.write(contents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getFile(long id) {
        File file = Arrays.stream(Objects.requireNonNull(new File(basePath).listFiles()))
                          .filter(f -> FilenameUtils.removeExtension(f.getName()).equals(String.valueOf(id)))
                          .findFirst()
                          .orElse(null);
        if (file == null) {
            // TODO: proper exception
            throw new RuntimeException("File not found");
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            // TODO: propert exception
            e.printStackTrace();
            return null;
        }
    }
}

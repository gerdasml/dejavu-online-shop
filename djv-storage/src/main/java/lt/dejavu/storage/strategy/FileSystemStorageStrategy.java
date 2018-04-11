package lt.dejavu.storage.strategy;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileSystemStorageStrategy implements StorageStrategy {

    private final String basePath;

    public FileSystemStorageStrategy(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveFile(byte[] contents, int id, String extension) {
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
    public byte[] getFile(int id) {
        File file = Arrays.stream(new File(basePath).listFiles())
                          .filter(f -> FilenameUtils.removeExtension(f.getName()).equalsIgnoreCase(String.valueOf(id)))
                          .findFirst()
                          .orElse(null);
        if(file == null) {
            // TODO: proper exception
            throw new RuntimeException("File not found");
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

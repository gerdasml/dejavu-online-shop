package lt.dejavu.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ExcelService<T> {
    ByteArrayOutputStream toExcel(List<T> items) throws IOException;

    UUID fromExcel(byte[] file) throws IOException;
}

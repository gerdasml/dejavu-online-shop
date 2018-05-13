package lt.dejavu.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ExcelService<T> {
    ByteArrayOutputStream toExcel(List<T> items) throws IOException;

    CompletableFuture<List<T>> fromExcel(File file) throws IOException;
}

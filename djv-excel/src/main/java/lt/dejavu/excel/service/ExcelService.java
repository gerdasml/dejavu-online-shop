package lt.dejavu.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ExcelService<T> {
    ByteArrayOutputStream toExcel(Collection<T> items) throws IOException;

    UUID fromExcel(byte[] file) throws IOException;
}

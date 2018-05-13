package lt.dejavu.excel.service;

import lt.dejavu.excel.model.ConversionResult;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

public interface ExcelService<T> {
    OutputStream toExcel(List<T> items) throws IOException;
    Stream<ConversionResult<T>> fromExcel(File file) throws IOException;
}

package lt.dejavu.excel.service;

import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.strategy.DataConversionStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ExcelServiceImpl<T> implements ExcelService<T> {
    private final DataConversionStrategy<T> strategy;

    public ExcelServiceImpl(DataConversionStrategy<T> strategy) {
        this.strategy = strategy;
    }

    @Override
    public OutputStream toExcel(List<T> items) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row headerRow = sheet.createRow(0);
        populateRow(headerRow, strategy.getHeader());
        AtomicInteger rowIndex = new AtomicInteger(1);
        items.stream()
             .map(strategy::toRows)
             .flatMap(Collection::stream)
             .forEach(rowData -> {
                 Row row = sheet.createRow(rowIndex.getAndIncrement());
                 populateRow(row, rowData);
             });

        OutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        return outputStream;
    }

    @Override
    public Stream<ConversionResult<T>> fromExcel(File file) throws IOException {
        return getRows(file).parallel()
                            .map(this::rowToStrings)
                            .map(strategy::toItem);
    }

    private void populateRow(Row row, List<String> values) {
        IntStream.range(0, values.size())
                 .forEach(idx -> {
                     Cell cell = row.createCell(idx);
                     cell.setCellValue(values.get(idx));
                 });
    }

    private List<String> rowToStrings(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        List<String> result = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            result.add(currentCell.getStringCellValue());
        }
        return result;
    }

    private Stream<Row> getRows(File file) throws IOException {
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        Iterable<Row> rowIterable = () -> iterator;
        return StreamSupport.stream(rowIterable.spliterator(), false);
    }
}

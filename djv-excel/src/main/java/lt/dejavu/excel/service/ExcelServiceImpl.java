package lt.dejavu.excel.service;

import lt.dejavu.excel.iterator.ConvertingIterator;
import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.strategy.ExcelConversionStrategy;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class ExcelServiceImpl<T> implements ExcelService<T> {
    private final ExcelConversionStrategy<T> conversionStrategy;
    private final ProcessingStrategy<T> processingStrategy;

    public ExcelServiceImpl(ExcelConversionStrategy<T> conversionStrategy, ProcessingStrategy<T> processingStrategy) {
        this.conversionStrategy = conversionStrategy;
        this.processingStrategy = processingStrategy;
    }

    @Override
    public ByteArrayOutputStream toExcel(List<T> items) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row headerRow = sheet.createRow(0);
        populateRow(headerRow, conversionStrategy.getHeader());
        AtomicInteger rowIndex = new AtomicInteger(1);
        items.stream()
             .map(conversionStrategy::toRows)
             .forEach(itemData -> {
                 int fromRow = rowIndex.get();
                 itemData.forEach(rowData -> {
                     Row row = sheet.createRow(rowIndex.getAndIncrement());
                     populateRow(row, rowData);
                 });
                 int toRow = rowIndex.get()-1;
                 conversionStrategy.getColumnsToMerge()
                                   .forEach(i ->
                                        sheet.addMergedRegion(
                                            new CellRangeAddress(fromRow, toRow, i ,i)
                                         )
                                   );
             });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        return outputStream;
    }

    @Override
    public CompletableFuture<List<T>> fromExcel(File file) throws IOException {
        PeekingIterator<List<String>> rowIterator = getIterator(file);
        return CompletableFuture.supplyAsync(() -> {
            List<T> errors = new ArrayList<>();
            while (rowIterator.hasNext()) {
                ConversionResult<T> result = conversionStrategy.takeOne(rowIterator);
                if (result.getStatus() == ConversionStatus.FAILURE) {
                    errors.add(result.getResult());
                } else {
                    processingStrategy.process(result.getResult());
                }
            }
            return errors;
        });
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

    private PeekingIterator<List<String>> getIterator(File file) throws IOException {
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        return new PeekingIterator<>(
                new ConvertingIterator<>(sheet.iterator(), this::rowToStrings)
        );
    }
}

package lt.dejavu.excel.service;

import lt.dejavu.excel.iterator.ConvertingIterator;
import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.strategy.ExcelConversionStrategy;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
        CellStyle cellStyle = getCellStyle(wb);
        Sheet sheet = wb.createSheet();
        setColumnWidths(sheet);
        Row headerRow = sheet.createRow(0);
        populateRow(headerRow, conversionStrategy.getHeader(), getHeaderCellStyle(wb));
        formatHeader(headerRow);
        AtomicInteger rowIndex = new AtomicInteger(1);
        items.stream()
             .map(conversionStrategy::toRows)
             .forEach(itemData -> {
                 int fromRow = rowIndex.get();
                 itemData.forEach(rowData -> {
                     Row row = sheet.createRow(rowIndex.getAndIncrement());
                     populateRow(row, rowData, cellStyle);
                 });
                 int toRow = rowIndex.get() - 1;
                 conversionStrategy.getColumnsToMerge()
                                   .forEach(i ->
                                                    sheet.addMergedRegion(
                                                            new CellRangeAddress(fromRow, toRow, i, i)
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

    private void formatHeader(Row headerRow) {
        int cellCount = headerRow.getPhysicalNumberOfCells();
        int last = 0;
        Sheet sheet = headerRow.getSheet();
        for (int i = 0; i < cellCount; i++) {
            String lastVal = headerRow.getCell(last).getStringCellValue();
            String currentVal = headerRow.getCell(i).getStringCellValue();
            if (!lastVal.equals(currentVal)) {
                if (last != i - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, last, i - 1));
                }
                last = i + 1;
            }
        }
        if (last != cellCount - 1) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, last, cellCount - 1));
        }
    }

    private void populateRow(Row row, List<String> values, CellStyle style) {
        IntStream.range(0, values.size())
                 .forEach(idx -> {
                     Cell cell = row.createCell(idx);
                     cell.setCellValue(values.get(idx));
                     cell.setCellStyle(style);
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

    private void setColumnWidths(Sheet sheet) {
        List<Integer> widths = conversionStrategy.getColumnWidths();
        IntStream.range(0, widths.size())
                 .forEach(idx -> sheet.setColumnWidth(idx, widths.get(idx)));
    }

    private CellStyle getCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    private CellStyle getHeaderCellStyle(Workbook wb) {
        CellStyle style = getCellStyle(wb);
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        return style;
    }
}

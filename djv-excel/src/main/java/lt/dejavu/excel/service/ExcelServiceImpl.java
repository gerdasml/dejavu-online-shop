package lt.dejavu.excel.service;

import javafx.util.Pair;
import lt.dejavu.excel.custom.OptimizedXSSFSheetDecorator;
import lt.dejavu.excel.iterator.ConvertingIterator;
import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.strategy.ExcelConversionStrategy;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.utils.debug.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class ExcelServiceImpl<T> implements ExcelService<T> {
    private final static Logger logger = LogManager.getLogger(ExcelServiceImpl.class);
    private final ExcelConversionStrategy<T> conversionStrategy;
    private final ProcessingStrategy<T> processingStrategy;

    public ExcelServiceImpl(ExcelConversionStrategy<T> conversionStrategy, ProcessingStrategy<T> processingStrategy) {
        this.conversionStrategy = conversionStrategy;
        this.processingStrategy = processingStrategy;
    }

    @Override
    public ByteArrayOutputStream toExcel(Collection<T> items) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CellStyle cellStyle = getCellStyle(wb);
        Sheet sheet = new OptimizedXSSFSheetDecorator(wb.createSheet());
        setColumnWidths(sheet);
        Row headerRow = sheet.createRow(0);
        createCells(headerRow, conversionStrategy.getHeader().size(), getHeaderCellStyle(wb));
        populateRow(headerRow, conversionStrategy.getHeader());
        formatHeader(headerRow);
        AtomicInteger rowIndex = new AtomicInteger(1);
        List<Pair<Integer, Integer>> mergeIntervals = new ArrayList<>();

        List<List<List<String>>> rows = Profiler.time("Map to rows", () -> items.stream().map(conversionStrategy::toRows).collect(toList()));
        List<List<Row>> excelRows = new ArrayList<>();
        Profiler.time("Create excel cells", () -> {
            rows.forEach(itemData -> {
                int fromRow = rowIndex.get();
                List<Row> innerRows = new ArrayList<>();
                itemData.forEach(rowData -> {
                    Row row = sheet.createRow(rowIndex.getAndIncrement());
                    innerRows.add(row);
                    createCells(row, rowData.size(), cellStyle);
                    //populateRow(row, rowData);
                });
                excelRows.add(innerRows);
                int toRow = rowIndex.get() - 1;
                mergeIntervals.add(new Pair<>(fromRow, toRow));
            });
        });
        Profiler.time("Populate cells", () -> {
            IntStream.range(0, rows.size()).parallel().forEach(rIdx -> {
                List<List<String>> dRows = rows.get(rIdx);
                List<Row> eRows = excelRows.get(rIdx);
                IntStream.range(0, dRows.size()).forEach(idx -> {
                    List<String> data = dRows.get(idx);
                    Row row = eRows.get(idx);
                    populateRow(row, data);
                });
            });
        });
        
        Profiler.time("Merge", () -> {
            conversionStrategy.getColumnsToMerge()
                              .parallelStream()
                              .forEach(col ->
                                               mergeIntervals.parallelStream()
                                                             .forEach(row ->
                                                                              sheet.addMergedRegionUnsafe(new CellRangeAddress(row.getKey(), row.getValue(), col, col))
                                                                     )
                                      );
        });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Profiler.time("Write", () -> {
            try {
                wb.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return outputStream;
    }

//    private void profile(String opName, Runnable runnable) {
//        logger.warn(String.format(">>> %s STARTED", opName));
//        long start = System.currentTimeMillis();
//        runnable.run();
//        long end = System.currentTimeMillis();
//        logger.warn(String.format("<<< %s ENDED (%s ms)", opName, String.valueOf(end-start)));
//    }

    @Override
    public UUID fromExcel(byte[] file) throws IOException {
        UUID uuid = UUID.randomUUID();
        CompletableFuture.runAsync(() -> {
            processingStrategy.start(uuid);
            try {
                analyze(uuid, getIterator(file));
                process(uuid, getIterator(file));
                processingStrategy.finish(uuid);
            } catch (Exception e) {
                logger.error(e);
                processingStrategy.fail(uuid);
            }
        });
        return uuid;
    }

    private void process(UUID jobId, PeekingIterator<List<String>> rowIterator) {
        rowIterator.next();
        while (rowIterator.hasNext()) {
            ConversionResult<T> result = conversionStrategy.takeOne(rowIterator);
            processingStrategy.process(jobId, result);
        }
    }

    private void analyze(UUID jobId, PeekingIterator<List<String>> rowIterator) {
        rowIterator.next();
        int counter = 0;
        while (rowIterator.hasNext()) {
            conversionStrategy.skipOne(rowIterator);
            counter++;
        }
        processingStrategy.finishAnalysis(jobId, counter);
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
                    last = i + 1;
                } else {
                    last = i;
                }
            }
        }
        if (last != cellCount - 1) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, last, cellCount - 1));
        }
    }

    private List<Cell> createCells(Row row, int count, CellStyle style) {
        return IntStream.range(0, count)
                        .boxed()
                        .map(idx -> {
                            Cell cell = row.createCell(idx);
                            cell.setCellStyle(style);
                            return cell;
                        })
                        .collect(toList());
    }

    private void populateRow(Row row, List<String> values) {
        IntStream.range(0, values.size())
                 .forEach(idx -> {
                     Cell cell = row.getCell(idx);
                     cell.setCellValue(values.get(idx));
                 });
    }

    private List<String> rowToStrings(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        List<String> result = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            currentCell.setCellType(CellType.STRING);
            result.add(currentCell.getStringCellValue());
        }
        return result;
    }

    private PeekingIterator<List<String>> getIterator(byte[] file) throws IOException {
        ByteArrayInputStream excelFile = new ByteArrayInputStream(file);
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

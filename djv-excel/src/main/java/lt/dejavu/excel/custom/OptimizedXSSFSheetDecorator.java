package lt.dejavu.excel.custom;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.util.Internal;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.helpers.ColumnHelper;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCellFormula;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCells;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class OptimizedXSSFSheetDecorator extends XSSFSheet {
    private final XSSFSheet sh;

    public OptimizedXSSFSheetDecorator(XSSFSheet sh) {
        this.sh = sh;
    }

    @Override
    public XSSFWorkbook getWorkbook() {
        return sh.getWorkbook();
    }

    @Override
    @Internal
    public CTWorksheet getCTWorksheet() {
        return sh.getCTWorksheet();
    }

    @Override
    public ColumnHelper getColumnHelper() {
        return sh.getColumnHelper();
    }

    @Override
    public String getSheetName() {
        return sh.getSheetName();
    }

    @Override
    public int addMergedRegion(CellRangeAddress region) {
        return sh.addMergedRegion(region);
    }

    /**
     * Implementation is copied from @see(org.apache.poi.xssf.usermodel.XSSFSheet).
     * The only difference is the return value, which caused a major slow down in the original implementation.
     */
    @Override
    public int addMergedRegionUnsafe(CellRangeAddress region) {
        if (region.getNumberOfCells() < 2) {
            throw new IllegalArgumentException("Merged region " + region.formatAsString() + " must contain 2 or more cells");
        } else {
            region.validate(SpreadsheetVersion.EXCEL2007);

            CTMergeCells ctMergeCells = sh.getCTWorksheet().isSetMergeCells() ? sh.getCTWorksheet().getMergeCells() : sh.getCTWorksheet().addNewMergeCells();
            CTMergeCell ctMergeCell = ctMergeCells.addNewMergeCell();
            ctMergeCell.setRef(region.formatAsString());
            return 0; // skip the built-in return for speed
        }
    }

    @Override
    public void validateMergedRegions() {
        sh.validateMergedRegions();
    }

    @Override
    public void autoSizeColumn(int column) {
        sh.autoSizeColumn(column);
    }

    @Override
    public void autoSizeColumn(int column, boolean useMergedCells) {
        sh.autoSizeColumn(column, useMergedCells);
    }

    @Override
    public XSSFDrawing getDrawingPatriarch() {
        return sh.getDrawingPatriarch();
    }

    @Override
    public XSSFDrawing createDrawingPatriarch() {
        return sh.createDrawingPatriarch();
    }

    @Override
    public void createFreezePane(int colSplit, int rowSplit) {
        sh.createFreezePane(colSplit, rowSplit);
    }

    @Override
    public void createFreezePane(int colSplit, int rowSplit, int leftmostColumn, int topRow) {
        sh.createFreezePane(colSplit, rowSplit, leftmostColumn, topRow);
    }

    @Override
    public XSSFRow createRow(int rownum) {
        return sh.createRow(rownum);
    }

    @Override
    public void createSplitPane(int xSplitPos, int ySplitPos, int leftmostColumn, int topRow, int activePane) {
        sh.createSplitPane(xSplitPos, ySplitPos, leftmostColumn, topRow, activePane);
    }

    @Override
    public XSSFComment getCellComment(CellAddress address) {
        return sh.getCellComment(address);
    }

    @Override
    public Map<CellAddress, XSSFComment> getCellComments() {
        return sh.getCellComments();
    }

    @Override
    public XSSFHyperlink getHyperlink(int row, int column) {
        return sh.getHyperlink(row, column);
    }

    @Override
    public XSSFHyperlink getHyperlink(CellAddress addr) {
        return sh.getHyperlink(addr);
    }

    @Override
    public List<XSSFHyperlink> getHyperlinkList() {
        return sh.getHyperlinkList();
    }

    @Override
    public int[] getColumnBreaks() {
        return sh.getColumnBreaks();
    }

    @Override
    public int getColumnWidth(int columnIndex) {
        return sh.getColumnWidth(columnIndex);
    }

    @Override
    public float getColumnWidthInPixels(int columnIndex) {
        return sh.getColumnWidthInPixels(columnIndex);
    }

    @Override
    public int getDefaultColumnWidth() {
        return sh.getDefaultColumnWidth();
    }

    @Override
    public short getDefaultRowHeight() {
        return sh.getDefaultRowHeight();
    }

    @Override
    public float getDefaultRowHeightInPoints() {
        return sh.getDefaultRowHeightInPoints();
    }

    @Override
    public CellStyle getColumnStyle(int column) {
        return sh.getColumnStyle(column);
    }

    @Override
    public void setRightToLeft(boolean value) {
        sh.setRightToLeft(value);
    }

    @Override
    public boolean isRightToLeft() {
        return sh.isRightToLeft();
    }

    @Override
    public boolean getDisplayGuts() {
        return sh.getDisplayGuts();
    }

    @Override
    public void setDisplayGuts(boolean value) {
        sh.setDisplayGuts(value);
    }

    @Override
    public boolean isDisplayZeros() {
        return sh.isDisplayZeros();
    }

    @Override
    public void setDisplayZeros(boolean value) {
        sh.setDisplayZeros(value);
    }

    @Override
    public int getFirstRowNum() {
        return sh.getFirstRowNum();
    }

    @Override
    public boolean getFitToPage() {
        return sh.getFitToPage();
    }

    @Override
    public Footer getFooter() {
        return sh.getFooter();
    }

    @Override
    public Header getHeader() {
        return sh.getHeader();
    }

    @Override
    public Footer getOddFooter() {
        return sh.getOddFooter();
    }

    @Override
    public Footer getEvenFooter() {
        return sh.getEvenFooter();
    }

    @Override
    public Footer getFirstFooter() {
        return sh.getFirstFooter();
    }

    @Override
    public Header getOddHeader() {
        return sh.getOddHeader();
    }

    @Override
    public Header getEvenHeader() {
        return sh.getEvenHeader();
    }

    @Override
    public Header getFirstHeader() {
        return sh.getFirstHeader();
    }

    @Override
    public boolean getHorizontallyCenter() {
        return sh.getHorizontallyCenter();
    }

    @Override
    public int getLastRowNum() {
        return sh.getLastRowNum();
    }

    @Override
    public short getLeftCol() {
        return sh.getLeftCol();
    }

    @Override
    public double getMargin(short margin) {
        return sh.getMargin(margin);
    }

    @Override
    public void setMargin(short margin, double size) {
        sh.setMargin(margin, size);
    }

    @Override
    public CellRangeAddress getMergedRegion(int index) {
        return sh.getMergedRegion(index);
    }

    @Override
    public List<CellRangeAddress> getMergedRegions() {
        return sh.getMergedRegions();
    }

    @Override
    public int getNumMergedRegions() {
        return sh.getNumMergedRegions();
    }

    @Override
    public int getNumHyperlinks() {
        return sh.getNumHyperlinks();
    }

    @Override
    public PaneInformation getPaneInformation() {
        return sh.getPaneInformation();
    }

    @Override
    public int getPhysicalNumberOfRows() {
        return sh.getPhysicalNumberOfRows();
    }

    @Override
    public XSSFPrintSetup getPrintSetup() {
        return sh.getPrintSetup();
    }

    @Override
    public boolean getProtect() {
        return sh.getProtect();
    }

    @Override
    public void protectSheet(String password) {
        sh.protectSheet(password);
    }

    @Override
    public void setSheetPassword(String password, HashAlgorithm hashAlgo) {
        sh.setSheetPassword(password, hashAlgo);
    }

    @Override
    public boolean validateSheetPassword(String password) {
        return sh.validateSheetPassword(password);
    }

    @Override
    public XSSFRow getRow(int rownum) {
        return sh.getRow(rownum);
    }

    @Override
    public int[] getRowBreaks() {
        return sh.getRowBreaks();
    }

    @Override
    public boolean getRowSumsBelow() {
        return sh.getRowSumsBelow();
    }

    @Override
    public void setRowSumsBelow(boolean value) {
        sh.setRowSumsBelow(value);
    }

    @Override
    public boolean getRowSumsRight() {
        return sh.getRowSumsRight();
    }

    @Override
    public void setRowSumsRight(boolean value) {
        sh.setRowSumsRight(value);
    }

    @Override
    public boolean getScenarioProtect() {
        return sh.getScenarioProtect();
    }

    @Override
    public short getTopRow() {
        return sh.getTopRow();
    }

    @Override
    public boolean getVerticallyCenter() {
        return sh.getVerticallyCenter();
    }

    @Override
    public void groupColumn(int fromColumn, int toColumn) {
        sh.groupColumn(fromColumn, toColumn);
    }

    @Override
    public void groupRow(int fromRow, int toRow) {
        sh.groupRow(fromRow, toRow);
    }

    @Override
    public boolean isColumnBroken(int column) {
        return sh.isColumnBroken(column);
    }

    @Override
    public boolean isColumnHidden(int columnIndex) {
        return sh.isColumnHidden(columnIndex);
    }

    @Override
    public boolean isDisplayFormulas() {
        return sh.isDisplayFormulas();
    }

    @Override
    public boolean isDisplayGridlines() {
        return sh.isDisplayGridlines();
    }

    @Override
    public void setDisplayGridlines(boolean show) {
        sh.setDisplayGridlines(show);
    }

    @Override
    public boolean isDisplayRowColHeadings() {
        return sh.isDisplayRowColHeadings();
    }

    @Override
    public void setDisplayRowColHeadings(boolean show) {
        sh.setDisplayRowColHeadings(show);
    }

    @Override
    public boolean isPrintGridlines() {
        return sh.isPrintGridlines();
    }

    @Override
    public void setPrintGridlines(boolean value) {
        sh.setPrintGridlines(value);
    }

    @Override
    public boolean isPrintRowAndColumnHeadings() {
        return sh.isPrintRowAndColumnHeadings();
    }

    @Override
    public void setPrintRowAndColumnHeadings(boolean value) {
        sh.setPrintRowAndColumnHeadings(value);
    }

    @Override
    public boolean isRowBroken(int row) {
        return sh.isRowBroken(row);
    }

    @Override
    public void setRowBreak(int row) {
        sh.setRowBreak(row);
    }

    @Override
    public void removeColumnBreak(int column) {
        sh.removeColumnBreak(column);
    }

    @Override
    public void removeMergedRegion(int index) {
        sh.removeMergedRegion(index);
    }

    @Override
    public void removeMergedRegions(Collection<Integer> indices) {
        sh.removeMergedRegions(indices);
    }

    @Override
    public void removeRow(Row row) {
        sh.removeRow(row);
    }

    @Override
    public void removeRowBreak(int row) {
        sh.removeRowBreak(row);
    }

    @Override
    public void setForceFormulaRecalculation(boolean value) {
        sh.setForceFormulaRecalculation(value);
    }

    @Override
    public boolean getForceFormulaRecalculation() {
        return sh.getForceFormulaRecalculation();
    }

    @Override
    public Iterator<Row> rowIterator() {
        return sh.rowIterator();
    }

    @Override
    public Iterator<Row> iterator() {
        return sh.iterator();
    }

    @Override
    public boolean getAutobreaks() {
        return sh.getAutobreaks();
    }

    @Override
    public void setAutobreaks(boolean value) {
        sh.setAutobreaks(value);
    }

    @Override
    public void setColumnBreak(int column) {
        sh.setColumnBreak(column);
    }

    @Override
    public void setColumnGroupCollapsed(int columnNumber, boolean collapsed) {
        sh.setColumnGroupCollapsed(columnNumber, collapsed);
    }

    @Override
    public void setColumnHidden(int columnIndex, boolean hidden) {
        sh.setColumnHidden(columnIndex, hidden);
    }

    @Override
    public void setColumnWidth(int columnIndex, int width) {
        sh.setColumnWidth(columnIndex, width);
    }

    @Override
    public void setDefaultColumnStyle(int column, CellStyle style) {
        sh.setDefaultColumnStyle(column, style);
    }

    @Override
    public void setDefaultColumnWidth(int width) {
        sh.setDefaultColumnWidth(width);
    }

    @Override
    public void setDefaultRowHeight(short height) {
        sh.setDefaultRowHeight(height);
    }

    @Override
    public void setDefaultRowHeightInPoints(float height) {
        sh.setDefaultRowHeightInPoints(height);
    }

    @Override
    public void setDisplayFormulas(boolean show) {
        sh.setDisplayFormulas(show);
    }

    @Override
    public void setFitToPage(boolean b) {
        sh.setFitToPage(b);
    }

    @Override
    public void setHorizontallyCenter(boolean value) {
        sh.setHorizontallyCenter(value);
    }

    @Override
    public void setVerticallyCenter(boolean value) {
        sh.setVerticallyCenter(value);
    }

    @Override
    public void setRowGroupCollapsed(int rowIndex, boolean collapse) {
        sh.setRowGroupCollapsed(rowIndex, collapse);
    }

    @Override
    public int findEndOfRowOutlineGroup(int row) {
        return sh.findEndOfRowOutlineGroup(row);
    }

    @Override
    public void setZoom(int scale) {
        sh.setZoom(scale);
    }

    @Override
    public void copyRows(List<? extends Row> srcRows, int destStartRow, CellCopyPolicy policy) {
        sh.copyRows(srcRows, destStartRow, policy);
    }

    @Override
    public void copyRows(int srcStartRow, int srcEndRow, int destStartRow, CellCopyPolicy cellCopyPolicy) {
        sh.copyRows(srcStartRow, srcEndRow, destStartRow, cellCopyPolicy);
    }

    @Override
    public void shiftRows(int startRow, int endRow, int n) {
        sh.shiftRows(startRow, endRow, n);
    }

    @Override
    public void shiftRows(int startRow, int endRow, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {
        sh.shiftRows(startRow, endRow, n, copyRowHeight, resetOriginalRowHeight);
    }

    @Override
    public void showInPane(int toprow, int leftcol) {
        sh.showInPane(toprow, leftcol);
    }

    @Override
    public void ungroupColumn(int fromColumn, int toColumn) {
        sh.ungroupColumn(fromColumn, toColumn);
    }

    @Override
    public void ungroupRow(int fromRow, int toRow) {
        sh.ungroupRow(fromRow, toRow);
    }

    @Override
    public boolean isSelected() {
        return sh.isSelected();
    }

    @Override
    public void setSelected(boolean value) {
        sh.setSelected(value);
    }

    @Override
    @Internal
    public void addHyperlink(XSSFHyperlink hyperlink) {
        sh.addHyperlink(hyperlink);
    }

    @Override
    @Internal
    public void removeHyperlink(int row, int column) {
        sh.removeHyperlink(row, column);
    }

    @Override
    public CellAddress getActiveCell() {
        return sh.getActiveCell();
    }

    @Override
    public void setActiveCell(CellAddress address) {
        sh.setActiveCell(address);
    }

    @Override
    public boolean hasComments() {
        return sh.hasComments();
    }

    @Override
    @Internal
    public CTCellFormula getSharedFormula(int sid) {
        return sh.getSharedFormula(sid);
    }

    @Override
    public boolean isAutoFilterLocked() {
        return sh.isAutoFilterLocked();
    }

    @Override
    public boolean isDeleteColumnsLocked() {
        return sh.isDeleteColumnsLocked();
    }

    @Override
    public boolean isDeleteRowsLocked() {
        return sh.isDeleteRowsLocked();
    }

    @Override
    public boolean isFormatCellsLocked() {
        return sh.isFormatCellsLocked();
    }

    @Override
    public boolean isFormatColumnsLocked() {
        return sh.isFormatColumnsLocked();
    }

    @Override
    public boolean isFormatRowsLocked() {
        return sh.isFormatRowsLocked();
    }

    @Override
    public boolean isInsertColumnsLocked() {
        return sh.isInsertColumnsLocked();
    }

    @Override
    public boolean isInsertHyperlinksLocked() {
        return sh.isInsertHyperlinksLocked();
    }

    @Override
    public boolean isInsertRowsLocked() {
        return sh.isInsertRowsLocked();
    }

    @Override
    public boolean isPivotTablesLocked() {
        return sh.isPivotTablesLocked();
    }

    @Override
    public boolean isSortLocked() {
        return sh.isSortLocked();
    }

    @Override
    public boolean isObjectsLocked() {
        return sh.isObjectsLocked();
    }

    @Override
    public boolean isScenariosLocked() {
        return sh.isScenariosLocked();
    }

    @Override
    public boolean isSelectLockedCellsLocked() {
        return sh.isSelectLockedCellsLocked();
    }

    @Override
    public boolean isSelectUnlockedCellsLocked() {
        return sh.isSelectUnlockedCellsLocked();
    }

    @Override
    public boolean isSheetLocked() {
        return sh.isSheetLocked();
    }

    @Override
    public void enableLocking() {
        sh.enableLocking();
    }

    @Override
    public void disableLocking() {
        sh.disableLocking();
    }

    @Override
    public void lockAutoFilter(boolean enabled) {
        sh.lockAutoFilter(enabled);
    }

    @Override
    public void lockDeleteColumns(boolean enabled) {
        sh.lockDeleteColumns(enabled);
    }

    @Override
    public void lockDeleteRows(boolean enabled) {
        sh.lockDeleteRows(enabled);
    }

    @Override
    public void lockFormatCells(boolean enabled) {
        sh.lockFormatCells(enabled);
    }

    @Override
    public void lockFormatColumns(boolean enabled) {
        sh.lockFormatColumns(enabled);
    }

    @Override
    public void lockFormatRows(boolean enabled) {
        sh.lockFormatRows(enabled);
    }

    @Override
    public void lockInsertColumns(boolean enabled) {
        sh.lockInsertColumns(enabled);
    }

    @Override
    public void lockInsertHyperlinks(boolean enabled) {
        sh.lockInsertHyperlinks(enabled);
    }

    @Override
    public void lockInsertRows(boolean enabled) {
        sh.lockInsertRows(enabled);
    }

    @Override
    public void lockPivotTables(boolean enabled) {
        sh.lockPivotTables(enabled);
    }

    @Override
    public void lockSort(boolean enabled) {
        sh.lockSort(enabled);
    }

    @Override
    public void lockObjects(boolean enabled) {
        sh.lockObjects(enabled);
    }

    @Override
    public void lockScenarios(boolean enabled) {
        sh.lockScenarios(enabled);
    }

    @Override
    public void lockSelectLockedCells(boolean enabled) {
        sh.lockSelectLockedCells(enabled);
    }

    @Override
    public void lockSelectUnlockedCells(boolean enabled) {
        sh.lockSelectUnlockedCells(enabled);
    }

    @Override
    public CellRange<XSSFCell> setArrayFormula(String formula, CellRangeAddress range) {
        return sh.setArrayFormula(formula, range);
    }

    @Override
    public CellRange<XSSFCell> removeArrayFormula(Cell cell) {
        return sh.removeArrayFormula(cell);
    }

    @Override
    public DataValidationHelper getDataValidationHelper() {
        return sh.getDataValidationHelper();
    }

    @Override
    public List<XSSFDataValidation> getDataValidations() {
        return sh.getDataValidations();
    }

    @Override
    public void addValidationData(DataValidation dataValidation) {
        sh.addValidationData(dataValidation);
    }

    @Override
    public XSSFAutoFilter setAutoFilter(CellRangeAddress range) {
        return sh.setAutoFilter(range);
    }

    @Override
    public XSSFTable createTable() {
        return sh.createTable();
    }

    @Override
    public List<XSSFTable> getTables() {
        return sh.getTables();
    }

    @Override
    public void removeTable(XSSFTable t) {
        sh.removeTable(t);
    }

    @Override
    public XSSFSheetConditionalFormatting getSheetConditionalFormatting() {
        return sh.getSheetConditionalFormatting();
    }

    @Override
    public XSSFColor getTabColor() {
        return sh.getTabColor();
    }

    @Override
    public void setTabColor(XSSFColor color) {
        sh.setTabColor(color);
    }

    @Override
    public CellRangeAddress getRepeatingRows() {
        return sh.getRepeatingRows();
    }

    @Override
    public CellRangeAddress getRepeatingColumns() {
        return sh.getRepeatingColumns();
    }

    @Override
    public void setRepeatingRows(CellRangeAddress rowRangeRef) {
        sh.setRepeatingRows(rowRangeRef);
    }

    @Override
    public void setRepeatingColumns(CellRangeAddress columnRangeRef) {
        sh.setRepeatingColumns(columnRangeRef);
    }

    @Override
    public XSSFPivotTable createPivotTable(AreaReference source, CellReference position, Sheet sourceSheet) {
        return sh.createPivotTable(source, position, sourceSheet);
    }

    @Override
    public XSSFPivotTable createPivotTable(AreaReference source, CellReference position) {
        return sh.createPivotTable(source, position);
    }

    @Override
    public XSSFPivotTable createPivotTable(Name source, CellReference position, Sheet sourceSheet) {
        return sh.createPivotTable(source, position, sourceSheet);
    }

    @Override
    public XSSFPivotTable createPivotTable(Name source, CellReference position) {
        return sh.createPivotTable(source, position);
    }

    @Override
    public XSSFPivotTable createPivotTable(Table source, CellReference position) {
        return sh.createPivotTable(source, position);
    }

    @Override
    public List<XSSFPivotTable> getPivotTables() {
        return sh.getPivotTables();
    }

    @Override
    public int getColumnOutlineLevel(int columnIndex) {
        return sh.getColumnOutlineLevel(columnIndex);
    }

    @Override
    public void addIgnoredErrors(CellReference cell, IgnoredErrorType... ignoredErrorTypes) {
        sh.addIgnoredErrors(cell, ignoredErrorTypes);
    }

    @Override
    public void addIgnoredErrors(CellRangeAddress region, IgnoredErrorType... ignoredErrorTypes) {
        sh.addIgnoredErrors(region, ignoredErrorTypes);
    }

    @Override
    public Map<IgnoredErrorType, Set<CellRangeAddress>> getIgnoredErrors() {
        return sh.getIgnoredErrors();
    }

    @Override
    public String toString() {
        return sh.toString();
    }

    @Internal
    @Deprecated
    public static void _invokeOnDocumentRead(POIXMLDocumentPart part) throws IOException {
        POIXMLDocumentPart._invokeOnDocumentRead(part);
    }

    @Override
    public void forEach(Consumer<? super Row> action) {
        sh.forEach(action);
    }

    @Override
    public Spliterator<Row> spliterator() {
        return sh.spliterator();
    }
}

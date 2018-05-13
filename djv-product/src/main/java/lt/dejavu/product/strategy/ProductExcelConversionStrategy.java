package lt.dejavu.product.strategy;

import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.strategy.ExcelConversionStrategy;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductPropertyDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class ProductExcelConversionStrategy implements ExcelConversionStrategy<ProductDto> {
    @Override
    public List<List<String>> toRows(ProductDto item) {
        List<ProductPropertyDto> properties = item.getProperties();
        if (properties == null || properties.size() == 0) {
            properties = new ArrayList<>();
            properties.add(new ProductPropertyDto());
        }
        List<List<String>> result = new ArrayList<>();
        List<String> firstRow = new ArrayList<>(
                Arrays.asList(
                        item.getName(),
                        item.getDescription(),
                        item.getPrice().toString(),
                        item.getCategoryId().toString(),
                        properties.get(0).getName(),
                        properties.get(0).getValue()
                             )
        );
        result.add(firstRow);
        properties.stream().skip(1).forEach(p -> {
            List<String> row = new ArrayList<>(Arrays.asList(
                    "", "", "", "", p.getName(), p.getValue()
                                                            ));
            result.add(row);
        });
        return result;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList(
                "Product Name",
                "Description",
                "Price",
                "Category",
                "Properties",
                "Properties"); // Repeat header name to merge properly
    }

    @Override
    public ConversionResult<ProductDto> takeOne(PeekingIterator<List<String>> rowIterator) {
        ConversionResult<ProductDto> result = rowToProduct(rowIterator.next());
        while (rowIterator.hasNext() && rowIterator.peek().size() <= 2) {
            List<String> row = rowIterator.next();
            readProperty(result, row, 0, 1);
        }
        return result;
    }

    @Override
    public List<Integer> getColumnsToMerge() {
        return Arrays.asList(0, 1, 2, 3);
    }

    @Override
    public List<Integer> getColumnWidths() {
        return Stream.of(19, 40, 12, 37, 31, 31)
                     .map(x -> x * 256)
                     .collect(toList());
    }

    private ConversionResult<ProductDto> rowToProduct(List<String> row) {
        ConversionResult<ProductDto> result = new ConversionResult<>();
        result.setStatus(ConversionStatus.SUCCESS);
        result.setResult(new ProductDto());
        result.getResult().setProperties(new ArrayList<>());

        read(0,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             val -> result.getResult().setName(val));
        read(1,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             val -> result.getResult().setDescription(val));
        read(2,
             row,
             result,
             this::isValidBigDecimal,
             BigDecimal::new,
             val -> result.getResult().setPrice(val));
        read(3,
             row,
             result,
             val -> true, // TODO: validate???
             val -> val,
             val -> result.getResult().setCategoryId(0L));
        readProperty(result, row, 4, 5);
        return result;
    }

    private void readProperty(ConversionResult<ProductDto> result,
                              List<String> row,
                              int nameIndex,
                              int valueIndex) {
        ProductPropertyDto property = new ProductPropertyDto();
        read(nameIndex,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             property::setName);
        read(valueIndex,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             property::setValue);
        if (property.getName() == null ^ property.getValue() == null) {
            result.setStatus(ConversionStatus.FAILURE);
        }
        if (property.getName() != null || property.getValue() != null) {
            result.getResult().getProperties().add(property);
        }
    }

    private boolean isValidBigDecimal(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        try {
            new BigDecimal(s);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private <T> void read(
            int columnIndex,
            List<String> row,
            ConversionResult<ProductDto> result,
            Predicate<String> validator,
            Function<String, T> converter,
            Consumer<T> setter) {
        if (row.size() <= columnIndex) {
            result.setStatus(ConversionStatus.FAILURE);
            return;
        }
        String value = row.get(columnIndex);
        if (!validator.test(value)) {
            result.setStatus(ConversionStatus.FAILURE);
            return;
        }
        T converted = converter.apply(value);
        setter.accept(converted);
    }
}

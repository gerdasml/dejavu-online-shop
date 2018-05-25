package lt.dejavu.product.strategy;

import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.strategy.ExcelConversionStrategy;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class ProductExcelConversionStrategy implements ExcelConversionStrategy<Product> {
    private final CategoryRepository categoryRepository;

    public ProductExcelConversionStrategy(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<List<String>> toRows(Product item) {
        List<ProductProperty> properties = new ArrayList<>(item.getProperties());
        if (properties.size() == 0) {
            properties = new ArrayList<>();
            properties.add(new ProductProperty());
        }
        List<List<String>> result = new ArrayList<>();
        List<String> firstRow =
                Arrays.asList(
                        item.getName(),
                        item.getPrice().toString(),
                        "SKU", // TODO: place actual SKU code
                        item.getDescription(),
                        item.getCategory().getIdentifier(),
                        properties.get(0).getCategoryProperty().getName(),
                        properties.get(0).getValue()
                 );
        result.add(firstRow);
        properties.stream().skip(1).forEach(p -> {
            List<String> row = new ArrayList<>(Arrays.asList(
                    "", "", "", "", "", p.getCategoryProperty().getName(), p.getValue()
                                                            ));
            result.add(row);
        });
        return result;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList(
                "Title",
                "Price",
                "SKU Code",
                "Description",
                "Category",
                "Properties",
                "Properties"); // Repeat header name to merge properly
    }

    @Override
    public ConversionResult<Product> takeOne(PeekingIterator<List<String>> rowIterator) {
        ConversionResult<Product> result = rowToProduct(rowIterator.next());
        while (rowIterator.hasNext() && rowIterator.peek().get(0).isEmpty()) {
            List<String> row = rowIterator.next();
            readProperty(result, row);
        }
        return result;
    }

    @Override
    public void skipOne(PeekingIterator<List<String>> rowIterator) {
        rowIterator.next();
        while (rowIterator.hasNext() && rowIterator.peek().get(0).isEmpty()) {
            rowIterator.next();
        }
    }

    @Override
    public List<Integer> getColumnsToMerge() {
        return Arrays.asList(0, 1, 2, 3, 4);
    }

    @Override
    public List<Integer> getColumnWidths() {
        return Stream.of(19, 15, 15, 40, 37, 31, 31)
                     .map(x -> x * 256)
                     .collect(toList());
    }

    private ConversionResult<Product> rowToProduct(List<String> row) {
        ConversionResult<Product> result = new ConversionResult<>();
        result.setStatus(ConversionStatus.SUCCESS);
        result.setResult(new Product());
        result.getResult().setProperties(new HashSet<>());

        readName(row, result);
        readPrice(row, result);
        readImage(row, result);
        readSkuCode(row, result);
        readDescription(row, result);
        readCategory(row, result);
        readProperty(result, row);
        return result;
    }

    private void readName(List<String> row, ConversionResult<Product> result) {
        read(0,
             row,
             result,
             val -> val != null && val.length() > 0,
             val -> val,
             val -> result.getResult().setName(val));
    }

    private void readPrice(List<String> row, ConversionResult<Product> result) {
        read(1,
             row,
             result,
             this::isValidBigDecimal,
             BigDecimal::new,
             val -> result.getResult().setPrice(val));
    }

    private void readImage(List<String> row, ConversionResult<Product> result) {
        // TODO: read image as path (columnIndex: 2)
    }

    private void readSkuCode(List<String> row, ConversionResult<Product> result) {
        // TODO: read SKU code (columnIndex: 3)
    }

    private void readDescription(List<String> row, ConversionResult<Product> result) {
        read(4,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             val -> result.getResult().setDescription(val));
    }

    private void readCategory(List<String> row, ConversionResult<Product> result) {
        read(5,
             row,
             result,
             this::isValidCategory,
             this::getCategory,
             val -> result.getResult().setCategory(val));
    }

    private void readProperty(ConversionResult<Product> result,
                              List<String> row) {
        Category productCategory = result.getResult().getCategory();
        if (productCategory == null) {
            return;
        }
        ProductProperty property = new ProductProperty();
        property.setProduct(result.getResult());
        read(6,
             row,
             result,
             isValidPropertyName(productCategory),
             getPropertyByName(productCategory),
             property::setCategoryProperty);
        if (result.getStatus().equals(ConversionStatus.FAILURE)) {
            return;
        }
        read(7,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             property::setValue);
        if (property.getCategoryProperty().getName() == null ^ property.getValue() == null) {
            result.setStatus(ConversionStatus.FAILURE);
        }
        if (property.getCategoryProperty().getName() != null || property.getValue() != null) {
            result.getResult().getProperties().add(property);
        }
    }

    private Category getCategory(String identifier) {
        return categoryRepository.getCategory(identifier);
    }

    private boolean isValidCategory(String identifier) {
        if (identifier == null || identifier.length() == 0) {
            return false;
        }
        return categoryRepository.getCategory(identifier) != null;
    }

    private Predicate<String> isValidPropertyName(Category category) {
        return s -> category.getProperties().stream().anyMatch(prop -> prop.getName().equals(s));
    }


    private Function<String, CategoryProperty> getPropertyByName(Category category) {
        return s -> category.getProperties().stream().filter(prop -> prop.getName().equals(s)).findFirst().orElseThrow(IllegalStateException::new);
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
            ConversionResult<Product> result,
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

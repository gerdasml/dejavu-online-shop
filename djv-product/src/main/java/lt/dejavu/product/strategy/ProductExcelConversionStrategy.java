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
                        item.getDescription(),
                        item.getPrice().toString(),
                        item.getCategory().getId().toString(), // TODO: identifier
                        properties.get(0).getCategoryProperty().getName(),
                        properties.get(0).getValue()
                 );
        result.add(firstRow);
        properties.stream().skip(1).forEach(p -> {
            List<String> row = new ArrayList<>(Arrays.asList(
                    "", "", "", "", p.getCategoryProperty().getName(), p.getValue()
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
        return Arrays.asList(0, 1, 2, 3);
    }

    @Override
    public List<Integer> getColumnWidths() {
        return Stream.of(19, 40, 12, 37, 31, 31)
                     .map(x -> x * 256)
                     .collect(toList());
    }

    private ConversionResult<Product> rowToProduct(List<String> row) {
        ConversionResult<Product> result = new ConversionResult<>();
        result.setStatus(ConversionStatus.SUCCESS);
        result.setResult(new Product());
        result.getResult().setProperties(new HashSet<>());

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
             this::isValidCategory,
             this::getCategory,
             val -> result.getResult().setCategory(val));
        readProperty(result, row);
        return result;
    }

    private void readProperty(ConversionResult<Product> result,
                              List<String> row) {
        Category productCategory = result.getResult().getCategory();
        if (result.getResult().getCategory() == null) {
            return;
        }
        ProductProperty property = new ProductProperty();
        property.setProduct(result.getResult());
        read(4,
             row,
             result,
             isValidPropertyName(productCategory),
             getPropertyByName(productCategory),
             property::setCategoryProperty);
        read(5,
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

    private Category getCategory(String s) {
        long id = Long.parseLong(s);
        return categoryRepository.getCategory(id);
    }

    private boolean isValidCategory(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        long id;
        try {
            id = Long.parseLong(s); // TODO: identifier
        } catch(Exception ignored) {
            return false;
        }
        return categoryRepository.getCategory(id) != null;
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

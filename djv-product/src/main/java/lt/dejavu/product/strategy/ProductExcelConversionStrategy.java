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
import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.model.ImageInfo;
import lt.dejavu.storage.image.service.ImageStorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.TriConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class ProductExcelConversionStrategy implements ExcelConversionStrategy<Product> {
    private final static Logger log = LoggerFactory.getLogger(ProductExcelConversionStrategy.class);
    private final static String IMAGE_PATH_SEPARATOR = ";";
    private final CategoryRepository categoryRepository;
    private final ImageStorageService imageStorageService;
    private final List<TriConsumer<Integer, List<String>, ConversionResult<Product>>> rowReaders = Arrays.asList(
            this::readName,
            this::readPrice,
            this::readImages,
            this::readSkuCode,
            this::readDescription,
            this::readCategory,
            this::readProperty
                                                                                                                );

    public ProductExcelConversionStrategy(CategoryRepository categoryRepository, ImageStorageService imageStorageService) {
        this.categoryRepository = categoryRepository;
        this.imageStorageService = imageStorageService;
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
                        item.getSkuCode(),
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
                "Product Name",
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
            if (row.stream().allMatch(r -> r == null || r.length() == 0)) {
                continue;
            }
            readProperty(6, row, result);
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

        IntStream.range(0, rowReaders.size())
                 .forEach(idx -> rowReaders.get(idx).accept(idx, row, result));
        return result;
    }

    private void readName(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             val -> val != null && val.length() > 0,
             val -> val,
             val -> result.getResult().setName(val));
    }

    private void readPrice(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             this::isValidBigDecimal,
             BigDecimal::new,
             val -> result.getResult().setPrice(val));
    }

    private void readImages(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             this::areAllImagePathsValid,
             this::getImageUrls,
             urls -> {
                if (urls.size() == 0) return;
                result.getResult().setMainImageUrl(urls.get(0));
                result.getResult().setAdditionalImagesUrls(new HashSet<>(urls.subList(1, urls.size())));
             });
    }

    private void readSkuCode(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             s -> s != null && s.length() > 0,
             s -> s,
             code -> result.getResult().setSkuCode(code));
    }

    private void readDescription(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             val -> result.getResult().setDescription(val));
    }

    private void readCategory(int columnIndex, List<String> row, ConversionResult<Product> result) {
        read(columnIndex,
             row,
             result,
             this::isValidCategory,
             this::getCategory,
             val -> result.getResult().setCategory(val));
    }

    private void readProperty(int columnIndex,
                              List<String> row,
                              ConversionResult<Product> result) {
        Category productCategory = result.getResult().getCategory();
        if (productCategory == null) {
            return;
        }
        if (StringUtils.isEmpty(row.get(columnIndex)) && StringUtils.isEmpty(row.get(columnIndex + 1))){
            return;
        }
        ProductProperty property = new ProductProperty();
        property.setProduct(result.getResult());
        read(columnIndex,
             row,
             result,
             isValidPropertyName(productCategory),
             getPropertyByName(productCategory),
             property::setCategoryProperty);
        if (property.getCategoryProperty() == null) {
            return;
        }
        read(columnIndex + 1,
             row,
             result,
             val -> true,
             val -> val == null ? "" : val,
             property::setValue);
        if (property.getCategoryProperty().getName() == null ^ property.getValue() == null) {
            result.setStatus(ConversionStatus.FAILURE);
            log.debug("CONVERSION FAILED:" + System.lineSeparator() + "product: " + result.getResult()  + System.lineSeparator() + " property: "+ property);
        }
        if (property.getCategoryProperty().getName() != null || property.getValue() != null) {
            result.getResult().getProperties().add(property);
        }
    }

    private boolean areAllImagePathsValid(String path) {
        String[] parts = path.split(Pattern.quote(IMAGE_PATH_SEPARATOR));
        return Arrays.stream(parts).allMatch(this::isValidImageFile);
    }

    private boolean isValidImageFile(String path) {
        path = path.trim();
        File f = new File(path);
        if (!f.exists() || !f.canRead()) return false;
        String ext = FilenameUtils.getExtension(f.getName());
        ImageFormat format = ImageFormat.resolve(ext);
        return format != ImageFormat.UNKNOWN;
    }

    private List<String> getImageUrls(String path) {
        return Arrays.stream(path.split(Pattern.quote(IMAGE_PATH_SEPARATOR)))
                     .map(String::trim)
                     .map(this::getImageUrl)
                     .collect(toList());
    }

    private String getImageUrl(String path) {
        File f = new File(path);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(f);
            ImageInfo info = new ImageInfo();
            info.setFilename(f.getName());
            info.setExtension(FilenameUtils.getExtension(f.getName()));
            info.setUploadDateTime(Timestamp.from(Instant.now()));
            ImageInfo imageInfo = imageStorageService.saveImage(bytes, info);
            return imageInfo.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        return s -> (
                category.getProperties().stream().anyMatch(prop -> prop.getName().equals(s))
                        ||
                category.getParentCategory() != null
                        &&
                category.getParentCategory().getProperties().stream().anyMatch(prop -> prop.getName().equals(s)));
    }


    private Function<String, CategoryProperty> getPropertyByName(Category category) {
        return s -> category.getProperties().stream().filter(prop -> prop.getName().equals(s)).findFirst().orElse(
                category.getParentCategory() != null ?
                category.getParentCategory().getProperties().stream().filter(prop -> prop.getName().equals(s)).findFirst().orElse(null) : null);
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
            log.debug("CONVERSION FAILED: row size mismatch");
            return;
        }
        String value = row.get(columnIndex);
            if (!validator.test(value)) {
                result.setStatus(ConversionStatus.FAILURE);
                log.debug("CONVERSION FAILED: validation failure: Value: " + value);
            return;
        }
        T converted = converter.apply(value);
        setter.accept(converted);
    }
}

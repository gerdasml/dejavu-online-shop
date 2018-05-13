package lt.dejavu.product.strategy;

import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.product.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessingStrategy implements ProcessingStrategy<ProductDto> {
    @Override
    public void process(ProductDto item) {
        System.out.println(String.format("Processing %s", item.getName()));
    }
}

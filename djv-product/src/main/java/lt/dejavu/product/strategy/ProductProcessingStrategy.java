package lt.dejavu.product.strategy;

import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.product.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductProcessingStrategy implements ProcessingStrategy<ProductDto> {
    @Override
    public void process(UUID jobId, ConversionResult<ProductDto> item) {
        if (item.getStatus() == ConversionStatus.SUCCESS) processSuccess(jobId, item.getResult());
        else processFailure(jobId, item.getResult());
    }

    private void processSuccess(UUID jobId, ProductDto product) {
        System.out.println(String.format("%s\t%s\tSUCCESS", jobId, product.getName()));
    }

    private void processFailure(UUID jobId, ProductDto product) {
        System.out.println(String.format("%s\t%s\tFAIL", jobId, product.getName()));
    }
}

package lt.dejavu.product.service;

import lt.dejavu.product.response.ProductImportStatusDto;

import java.util.List;
import java.util.UUID;

public interface ProductImportStatusService {
    List<ProductImportStatusDto> getAllStatuses();
    ProductImportStatusDto getStatus(UUID jobId);
    ProductImportStatusDto updateStatus(UUID jobId, ProductImportStatusDto newStatus);
}

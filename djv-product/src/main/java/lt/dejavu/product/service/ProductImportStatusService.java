package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductImportStatusDto;

import java.util.List;
import java.util.UUID;

public interface ProductImportStatusService {
    List<ProductImportStatusDto> getAllStatuses();
    ProductImportStatusDto getStatus(UUID jobId);
    ProductImportStatusDto getStatistics(UUID jobId);
    ProductImportStatusDto updateStatus(UUID jobId, ProductImportStatusDto newStatus);
}

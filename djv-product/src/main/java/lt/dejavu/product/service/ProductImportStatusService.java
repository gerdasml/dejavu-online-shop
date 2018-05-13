package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductImportStatusDto;

import java.util.UUID;

public interface ProductImportStatusService {
    ProductImportStatusDto getStatus(UUID jobId);
}

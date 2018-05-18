package lt.dejavu.product.service;

import lt.dejavu.product.response.ProductImportStatusResponse;

import java.util.List;
import java.util.UUID;

public interface ProductImportStatusService {
    List<ProductImportStatusResponse> getAllStatuses();
    ProductImportStatusResponse getStatus(UUID jobId);
    ProductImportStatusResponse updateStatus(UUID jobId, ProductImportStatusResponse newStatus);
}

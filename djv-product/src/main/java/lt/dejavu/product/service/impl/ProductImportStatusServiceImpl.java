package lt.dejavu.product.service.impl;

import lt.dejavu.excel.repository.ImportStatusRepository;
import lt.dejavu.product.response.ProductImportStatusResponse;
import lt.dejavu.product.response.mapper.ProductImportStatusResponseMapper;
import lt.dejavu.product.service.ProductImportStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductImportStatusServiceImpl implements ProductImportStatusService {
    private final ProductImportStatusResponseMapper mapper;
    private final ImportStatusRepository importStatusRepository;

    public ProductImportStatusServiceImpl(ProductImportStatusResponseMapper mapper, ImportStatusRepository importStatusRepository) {
        this.mapper = mapper;
        this.importStatusRepository = importStatusRepository;
    }

    @Override
    public List<ProductImportStatusResponse> getAllStatuses() {
        return mapper.map(importStatusRepository.getAllImportStatuses());
    }

    @Override
    public ProductImportStatusResponse getStatus(UUID jobId) {
        return mapper.map(importStatusRepository.getImportStatus(jobId));
    }

    @Override
    public ProductImportStatusResponse updateStatus(UUID jobId, ProductImportStatusResponse newStatus) {
        newStatus.setId(jobId);
        importStatusRepository.updateImportStatus(mapper.map(newStatus));
        return getStatus(jobId);
    }
}

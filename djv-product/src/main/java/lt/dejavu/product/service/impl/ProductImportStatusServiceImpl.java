package lt.dejavu.product.service.impl;

import lt.dejavu.excel.repository.ImportStatusRepository;
import lt.dejavu.product.dto.ProductImportStatusDto;
import lt.dejavu.product.dto.mapper.ProductImportStatusDtoMapper;
import lt.dejavu.product.service.ProductImportStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductImportStatusServiceImpl implements ProductImportStatusService {
    private final ProductImportStatusDtoMapper mapper;
    private final ImportStatusRepository importStatusRepository;

    public ProductImportStatusServiceImpl(ProductImportStatusDtoMapper mapper, ImportStatusRepository importStatusRepository) {
        this.mapper = mapper;
        this.importStatusRepository = importStatusRepository;
    }

    @Override
    public List<ProductImportStatusDto> getAllStatuses() {
        return mapper.map(importStatusRepository.getAllImportStatuses());
    }

    @Override
    public ProductImportStatusDto getStatus(UUID jobId) {
        return mapper.map(importStatusRepository.getImportStatus(jobId));
    }
}
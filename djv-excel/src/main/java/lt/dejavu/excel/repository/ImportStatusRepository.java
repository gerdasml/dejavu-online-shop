package lt.dejavu.excel.repository;

import lt.dejavu.excel.model.db.ImportStatus;

import java.util.List;
import java.util.UUID;

public interface ImportStatusRepository {
    List<ImportStatus> getAllImportStatuses();

    ImportStatus getImportStatusWithFailures(UUID id);

    ImportStatus getImportStatistics(UUID id);

    void createImportStatus(ImportStatus status);

    void updateImportStatus(ImportStatus newStatus);

    void deleteImportStatus(UUID id);
}

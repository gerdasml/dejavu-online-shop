package lt.dejavu.excel.repository;

import lt.dejavu.excel.model.db.ImportStatus;

import java.util.UUID;

public interface ImportStatusRepository {
    ImportStatus getImportStatus(UUID id);

    void createImportStatus(ImportStatus status);

    void updateImportStatus(ImportStatus newStatus);

    void deleteImportStatus(UUID id);
}

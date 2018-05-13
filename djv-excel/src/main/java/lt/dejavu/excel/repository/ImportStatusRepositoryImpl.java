package lt.dejavu.excel.repository;

import lt.dejavu.excel.model.db.ImportStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public class ImportStatusRepositoryImpl implements ImportStatusRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public ImportStatus getImportStatus(UUID id) {
        return em.find(ImportStatus.class, id);
    }

    @Override
    public void createImportStatus(ImportStatus status) {
        em.persist(status);
    }

    @Override
    public void updateImportStatus(ImportStatus newStatus) {
        em.merge(newStatus);
    }

    @Override
    public void deleteImportStatus(UUID id) {
        ImportStatus current = getImportStatus(id);
        em.remove(current);
    }
}

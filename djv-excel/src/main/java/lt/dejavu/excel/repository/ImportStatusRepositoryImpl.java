package lt.dejavu.excel.repository;

import lt.dejavu.excel.model.db.ImportStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class ImportStatusRepositoryImpl implements ImportStatusRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ImportStatus> getAllImportStatuses() {
        // TODO: extract this code to some common place, because we'll probably need it in multiple places
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ImportStatus> cq = cb.createQuery(ImportStatus.class);
        Root<ImportStatus> rootEntry = cq.from(ImportStatus.class);
        CriteriaQuery<ImportStatus> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

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

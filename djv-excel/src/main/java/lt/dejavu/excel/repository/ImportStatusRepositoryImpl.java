package lt.dejavu.excel.repository;

import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.excel.model.db.ImportStatus_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Repository
@Transactional
public class ImportStatusRepositoryImpl implements ImportStatusRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public ImportStatus getImportStatistics(UUID id) {
        return em.find(ImportStatus.class, id);
    }

    @Override
    public ImportStatus getImportStatusWithFailures(UUID id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ImportStatus> cq = cb.createQuery(ImportStatus.class);
        Root<ImportStatus> root = cq.from(ImportStatus.class);
        root.join(ImportStatus_.failedItems, JoinType.LEFT);
        CriteriaQuery<ImportStatus>  query = cq.select(root);
        ParameterExpression<UUID> idParam = cb.parameter(UUID.class);
        query.where(cb.equal(root.get(ImportStatus_.id), idParam));
        return em.createQuery(query).setParameter(idParam, id).getSingleResult();
    }

    @Override
    public List<ImportStatus> getAllImportStatuses() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ImportStatus> cq = cb.createQuery(ImportStatus.class);
        Root<ImportStatus> rootEntry = cq.from(ImportStatus.class);
        rootEntry.join(ImportStatus_.failedItems, JoinType.LEFT);
        CriteriaQuery<ImportStatus> all = cq.select(rootEntry).distinct(true);
        return em.createQuery(all).getResultList();
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
        ImportStatus current = getImportStatistics(id);
        em.remove(current);
    }
}

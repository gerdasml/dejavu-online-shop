package lt.dejavu.storage.image.repository;

import lt.dejavu.storage.image.model.db.Image;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ImageRepositoryImpl implements ImageRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Image> getImages() {
        // TODO: extract this code to some common place, because we'll probably need it in multiple places
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);
        Root<Image> rootEntry = cq.from(Image.class);
        CriteriaQuery<Image> all = cq.select(rootEntry);
        TypedQuery<Image> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Image getImage(long id) {
        return em.find(Image.class, id);
    }

    @Override
    public long saveImage(Image image) {
        em.persist(image);
        return image.getId();
    }
}

package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.model.db.User_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public Long getUserId(String email, String password) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate condition =
                cb.and(
                    cb.equal(root.get(User_.email), email),
                    cb.equal(root.get(User_.password), password)
                );
        query.where(condition);
        TypedQuery<User> q = em.createQuery(query);
        return q.getResultList()
                .stream()
                .findFirst()
                .map(User::getId)
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    @Override
    public void setBanned(long id, boolean isBanned) {
        User user = em.find(User.class, id);
        user.setBanned(isBanned);
    }

    @Override
    public void updateUserInfo(long id, User info) {
        throw new NotImplementedException();
    }

    @Override
    public Long addUser(User user) {
        em.persist(user);
        return user.getId();
    }
}

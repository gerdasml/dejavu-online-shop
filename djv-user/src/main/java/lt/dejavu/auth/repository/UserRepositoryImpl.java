package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.db.Address;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.model.db.User_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean emailExists(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.equal(root.get(User_.email), email));
        TypedQuery<User> q = em.createQuery(query);
        return q.getResultList().size() > 0;
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
    public void setBanned(User user, boolean isBanned) {
        user.setBanned(isBanned);
    }

    @Override
    public void updateUserInfo(User oldUser, User newUser) {
        updateUserInfo(oldUser, newUser, false);
    }

    @Override
    public void updateUserInfo(User oldUser, User newUser, boolean updatePassword) {
        mergeInformation(oldUser, newUser, updatePassword);
        em.merge(newUser.getAddress());
        em.merge(newUser);
    }

    @Override
    public Long addUser(User user) {
        if (user.getAddress() == null) {
            user.setAddress(new Address());
        }
        em.persist(user.getAddress());
        em.persist(user);
        return user.getId();
    }

    private void mergeInformation(User oldUser, User newUser, boolean updatePassword) {
        newUser.setId(oldUser.getId());
        if (!updatePassword) {
            newUser.setPassword(oldUser.getPassword());
        }
        newUser.setType(oldUser.getType());
        newUser.setBanned(oldUser.isBanned());
        newUser.setEmail(oldUser.getEmail());

        if (newUser.getAddress() == null) {
            newUser.setAddress(new Address());
        }
        if (oldUser.getAddress() != null) {
            newUser.getAddress().setId(oldUser.getAddress().getId());
        }
    }
}

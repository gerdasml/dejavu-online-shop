package lt.dejavu.order.repository;

import lt.dejavu.auth.model.db.User_;
import lt.dejavu.order.exception.OrderNotFoundException;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.db.Order;
import lt.dejavu.order.model.db.Order_;
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
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Order getOrder(long id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> getOrders() {
        // TODO: extract this code to some common place, because we'll probably need it in multiple places
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> rootEntry = cq.from(Order.class);
        CriteriaQuery<Order> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    @Override
    public List<Order> getOrdersByUser(long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Predicate condition = cb.equal(root.get(Order_.user).get(User_.id), userId);
        query.where(condition);
        TypedQuery<Order> q = em.createQuery(query);
        return q.getResultList();
    }

    @Override
    public long saveOrder(Order order) {
        em.persist(order);
        em.persist(order.getShippingAddress());
        order.getItems().forEach(em::persist);
        return order.getId();
    }

    @Override
    public void updateOrderStatus(long orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        if (order == null) {
            throw new OrderNotFoundException("The order with the specified ID was not found");
        }
        order.setStatus(status);
        em.merge(order);
    }
}

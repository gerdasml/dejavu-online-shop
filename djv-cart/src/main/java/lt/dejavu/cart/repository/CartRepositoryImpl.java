package lt.dejavu.cart.repository;

import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.model.db.User_;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.cart.model.db.Cart_;
import lt.dejavu.cart.util.CartUtil;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.product.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Optional;

@Repository
@Transactional
public class CartRepositoryImpl implements CartRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Cart> getCartByUserId(long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
        Root<Cart> root = query.from(Cart.class);
        Predicate condition = cb.equal(root.get(Cart_.user).get(User_.id), userId);
        query.where(condition);
        TypedQuery<Cart> q = em.createQuery(query);

        return q.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void removeItem(Cart cart, long productId) {
        Optional<OrderItem> item = CartUtil.getOrderItemByProductId(cart, productId);
        if (item.isPresent()) {
            cart.getItems().remove(item.get());
            em.remove(item.get());
        }
    }

    @Override
    public void addOrderItem(Cart cart, Product product, int amount) {
        OrderItem item = new OrderItem(product);
        item.setAmount(amount);
        em.persist(item);
        cart.getItems().add(item);
    }

    @Override
    public void updateOrderItem(OrderItem item) {
        em.merge(item);
    }

    @Override
    public Cart createEmptyCart(User user) {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        em.persist(cart);

        return cart;
    }

    @Override
    public void deleteCart(long userId) {
        Optional<Cart> cartOpt = getCartByUserId(userId);
        cartOpt.ifPresent(cart -> em.remove(cart));
    }
}

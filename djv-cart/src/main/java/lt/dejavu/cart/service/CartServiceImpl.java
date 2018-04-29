package lt.dejavu.cart.service;

import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.cart.exception.ProductAlreadyInCartException;
import lt.dejavu.cart.exception.ProductNotInCartException;
import lt.dejavu.cart.mapper.CartMapper;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.cart.repository.CartRepository;
import lt.dejavu.order.model.db.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDto getCart(long userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        return cartMapper.map(cart);
    }

    @Override
    public void addToCart(long userId, long productId, int amount) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if(cartRepository.getOrderItemByProductId(cart, productId) != null) {
            throw new ProductAlreadyInCartException("This product is already in your cart");
        }
        // TODO: add product to cart
    }

    @Override
    public void updateAmount(long userId, long productId, int amount) {
        Cart cart = cartRepository.getCartByUserId(userId);
        OrderItem item = cartRepository.getOrderItemByProductId(cart, productId);
        if(item == null) {
            throw new ProductNotInCartException("This product is not in your cart");
        }
        // TODO: update product amount
    }

    @Override
    public void removeProduct(long userId, long productId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        cartRepository.removeItem(cart, productId);
    }
}

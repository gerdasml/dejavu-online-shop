package lt.dejavu.cart.service;

import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.cart.exception.ProductAlreadyInCartException;
import lt.dejavu.cart.exception.ProductNotInCartException;
import lt.dejavu.cart.mapper.CartMapper;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.cart.repository.CartRepository;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
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
        Product product = productRepository.getProduct(productId);
        if(product == null) {
            throw new ProductNotFoundException("The product with the specified ID was nto found");
        }
        cartRepository.addOrderItem(cart, product, amount);
    }

    @Override
    public void updateAmount(long userId, long productId, int amount) {
        Cart cart = cartRepository.getCartByUserId(userId);
        Optional<OrderItem> itemOpt = cartRepository.getOrderItemByProductId(cart, productId);
        if(!itemOpt.isPresent()) {
            throw new ProductNotInCartException("This product is not in your cart");
        }
        OrderItem item = itemOpt.get();
        item.setAmount(amount);
        cartRepository.updateOrderItem(item);
    }

    @Override
    public void removeProduct(long userId, long productId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        cartRepository.removeItem(cart, productId);
    }
}

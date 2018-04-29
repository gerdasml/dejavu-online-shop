package lt.dejavu.cart.service;

import lt.dejavu.cart.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartDto getCart(long userId) {
        return null;
    }

    @Override
    public void addToCart(long userId, long productId, int amount) {

    }

    @Override
    public void updateAmount(long userId, long productId, int amount) {

    }

    @Override
    public void removeProduct(long userId, long productId) {

    }
}

package lt.dejavu.cart;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.cart.model.rest.CartResponse;
import lt.dejavu.cart.model.rest.CartRequest;
import lt.dejavu.cart.model.rest.CheckoutRequest;
import lt.dejavu.cart.service.CartService;
import lt.dejavu.payment.exception.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${rest.cart}")
public class CartApi {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public CartResponse getCart(HttpServletRequest request,
                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader
                               ) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return cartService.getCart(userId);
    }

    @PostMapping("/")
    public CartResponse addToCart(HttpServletRequest request,
                                  @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                  @RequestBody CartRequest requestData
                                 ) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return cartService.addToCart(userId, requestData.getProductId(), requestData.getAmount());
    }

    @PutMapping("/")
    public CartResponse updateAmount(HttpServletRequest request,
                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                     @RequestBody CartRequest requestData
                                    ) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return cartService.updateAmount(userId, requestData.getProductId(), requestData.getAmount());
    }

    @DeleteMapping("/{productId}")
    public CartResponse removeProduct(HttpServletRequest request,
                                      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                      @PathVariable("productId") long productId
                                     ) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return cartService.removeProduct(userId, productId);
    }

    @PostMapping("/checkout")
    public void checkout(HttpServletRequest request,
                         @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                         @RequestBody CheckoutRequest checkoutRequest
                        ) throws ApiSecurityException, PaymentException {
        long userId = securityService.authorize(authHeader, request);
        cartService.checkout(userId, checkoutRequest.getCard(), checkoutRequest.getShippingAddress());
    }
}

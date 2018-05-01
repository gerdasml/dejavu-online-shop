package lt.dejavu.cart;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.cart.model.rest.CartRequest;
import lt.dejavu.cart.service.CartService;
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
    public CartDto getCart(HttpServletRequest request,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
                          ) throws ApiSecurityException {
        long userId = authorize(authHeader, request);
        return cartService.getCart(userId);
    }

    @PostMapping("/")
    public void addToCart(HttpServletRequest request,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                          @RequestBody CartRequest requestData
                          ) throws ApiSecurityException {
        long userId = authorize(authHeader, request);
        cartService.addToCart(userId, requestData.getProductId(), requestData.getAmount());
    }

    @PutMapping("/")
    public void updateAmount(HttpServletRequest request,
                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                             @RequestBody CartRequest requestData
                            ) throws ApiSecurityException {
        long userId = authorize(authHeader, request);
        cartService.updateAmount(userId, requestData.getProductId(), requestData.getAmount());
    }

    @DeleteMapping("/{productId}")
    public void removeProduct(HttpServletRequest request,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                              @PathVariable("productId") long productId
                             ) throws ApiSecurityException {
        long userId = authorize(authHeader, request);
        cartService.removeProduct(userId, productId);
    }

    private Endpoint buildEndpoint(HttpServletRequest request) {
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(RequestMethod.valueOf(request.getMethod()));
        endpoint.setPath(request.getRequestURI());
        return endpoint;
    }

    private long authorize(String authHeader, HttpServletRequest request) throws ApiSecurityException {
        return securityService.authorize(authHeader, buildEndpoint(request));
    }
}

package lt.dejavu.discount;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.service.DiscountService;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${rest.discount}")
public class DiscountApi {
    @Autowired
    private DiscountService discountService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public List<DiscountDto> getAllDiscounts(HttpServletRequest request,
                                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader
                                             ) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return discountService.getAllDiscounts();
    }

    @GetMapping("/{discountId}")
    public DiscountDto getDiscount(HttpServletRequest request,
                                   @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                   @PathVariable("discountId") long discountId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return discountService.getDiscountById(discountId);
    }

    @PostMapping("/")
    public long addDiscount(HttpServletRequest request,
                            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                            @RequestBody DiscountDto discount) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return discountService.addDiscount(discount);
    }

    @PutMapping("/{id}")
    public void updateDiscount(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @PathVariable("id") long id,
                               @RequestBody DiscountDto discount) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        discountService.updateDiscount(id, discount);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscount(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @PathVariable("id") long id) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        discountService.deleteDiscount(id);
    }
}

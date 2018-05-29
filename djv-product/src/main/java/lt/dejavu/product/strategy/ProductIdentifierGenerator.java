package lt.dejavu.product.strategy;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.utils.common.FormattingUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductIdentifierGenerator implements IdentifierGenerator<Product> {

    @Override
    public String generateIdentifier(Product product) {
        String result = "";
        if (product.getCategory() != null) {
            if (product.getCategory().getIdentifier() != null) {
                result = product.getCategory().getIdentifier() + "/";
            }
        }
        result += FormattingUtils.toUrlFriendlyString(product.getName() + " " + product.getId());
        return result;
    }
}

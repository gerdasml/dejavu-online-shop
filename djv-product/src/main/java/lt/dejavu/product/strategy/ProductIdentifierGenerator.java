package lt.dejavu.product.strategy;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.utils.common.FormattingUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductIdentifierGenerator implements IdentifierGenerator<Product> {
    private final IdentifierGenerator<Category> categoryIdentifierGenerator;

    public ProductIdentifierGenerator(IdentifierGenerator<Category> categoryIdentifierGenerator) {
        this.categoryIdentifierGenerator = categoryIdentifierGenerator;
    }

    @Override
    public String generateIdentifier(Product product) {
        String result = "";
        if (product.getCategory() != null) {
            if (product.getCategory().getIdentifier() != null) {
                result = product.getCategory().getIdentifier() + "/";
            } else {
                result += categoryIdentifierGenerator.generateIdentifier(product.getCategory()) + "/";
            }
        }
        result += FormattingUtils.toUrlFriendlyString(product.getName() + " " + product.getId());
        return result;
    }
}

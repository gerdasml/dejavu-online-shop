package lt.dejavu.product.strategy;

import lt.dejavu.product.model.Category;
import lt.dejavu.utils.common.FormattingUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdentifierGenerator implements IdentifierGenerator<Category> {
    @Override
    public String generateIdentifier(Category category) {
        if (category == null) return null;
        String result = "";
        if (category.getParentCategory() != null) {
            result += generateIdentifier(category.getParentCategory()) + "/";
        }
        result += FormattingUtils.toUrlFriendlyString(category.getName());
        return result;
    }
}

package lt.dejavu.product.view.mapper;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductViewMapper {

    @Autowired
    private CategoryViewMapper categoryViewMapper;

    public ProductView map(Product product) {
        return map(product, false);
    }

    public ProductView map(Product product, boolean fetched) {
        if (product == null) {
            return null;
        }
        ProductView view = new ProductView();
        view.setId(product.getId());
        view.setName(product.getName());
        view.setDescription(product.getDescription());
        view.setCreationDate(product.getCreationDate());
        if (product.getCategory() != null) {
            view.setCategoryId(product.getCategory().getId());
            if (fetched) {
                view.setCategory(categoryViewMapper.map(product.getCategory(),true ));
            }
        }
        return view;
    }

    public List<ProductView> map(List<Product> products) {
        return map(products, false);
    }

    public List<ProductView> map(List<Product> products, boolean fetched) {
        return products.stream().map(c -> map(c, fetched)).collect(Collectors.toList());
    }
}

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

    public ProductView map(Product product, boolean fetched) {
        ProductView view = new ProductView();
        view.setId(product.getId());
        view.setName(product.getName());
        view.setDescription(product.getDescription());
        view.setCreationDate(product.getCreationDate());
        if (fetched && product.getCategory() != null) {
            view.setCategory(categoryViewMapper.map(product.getCategory(),true ));
        }
        return view;
    }

    public List<ProductView> map(List<Product> categories, boolean fetched) {
        return categories.stream().map(c -> map(c, fetched)).collect(Collectors.toList());
    }
}

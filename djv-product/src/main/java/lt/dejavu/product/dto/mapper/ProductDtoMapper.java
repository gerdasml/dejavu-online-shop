package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    public ProductDto map(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto view = new ProductDto();
        view.setId(product.getId());
        view.setName(product.getName());
        view.setDescription(product.getDescription());
        view.setCreationDate(product.getCreationDate());
        if (product.getCategory() != null) {
            view.setCategoryId(product.getCategory().getId());
        }
        return view;
    }

    public List<ProductDto> map(List<Product> products) {
        return products.stream().map(this::map).collect(Collectors.toList());
    }
}

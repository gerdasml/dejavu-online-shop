package lt.dejavu.onlineshop.repository;

import lt.dejavu.onlineshop.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

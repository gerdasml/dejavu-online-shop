package com.dejavu.onlineshop.repository;

import com.dejavu.onlineshop.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

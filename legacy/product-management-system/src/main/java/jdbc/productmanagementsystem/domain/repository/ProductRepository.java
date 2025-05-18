package jdbc.productmanagementsystem.domain.repository;

import jdbc.productmanagementsystem.domain.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
}

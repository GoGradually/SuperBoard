package jdbc.productmanagementsystem.domain.repository;

import jdbc.productmanagementsystem.domain.model.product.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);
    Product findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
}

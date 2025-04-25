package jdbc.productmanagementsystem.infra.persistence;

import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryJDBC implements ProductRepository {
    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

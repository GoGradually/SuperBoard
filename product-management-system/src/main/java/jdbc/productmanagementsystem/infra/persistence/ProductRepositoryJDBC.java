package jdbc.productmanagementsystem.infra.persistence;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryJDBC implements ProductRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public Product save(Product product) {

        if (product.getId() != null) {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(Map.of(
                    "productName", product.getProductName(),
                    "price", product.getPrice(),
                    "quantity", product.getQuantity(),
                    "id", product.getId()
            ));
            int updated = template.update("update product set product_name = :productName, price = :price, quantity = :quantity where id = :id", sqlParameterSource);
            if(updated == 0){
                throw new ProductNotFoundException("Product not found");
            }
            return product;
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(Map.of(
                "productName", product.getProductName(),
                "price", product.getPrice(),
                "quantity", product.getQuantity()
        ));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into product (product_name, price, quantity) values (:productName, :price, :quantity)";
        template.update(sql, sqlParameterSource, keyHolder);
        try {
            Field id = Product.class.getDeclaredField("id");
            id.setAccessible(true);
            id.set(product, Objects.requireNonNull(keyHolder.getKey()).longValue());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}

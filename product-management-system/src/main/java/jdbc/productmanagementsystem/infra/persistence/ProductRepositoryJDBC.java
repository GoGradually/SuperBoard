package jdbc.productmanagementsystem.infra.persistence;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jdbc.productmanagementsystem.infra.persistence.RepositoryJDBCUtils.fillId;

@Repository
public class ProductRepositoryJDBC implements ProductRepository {

    private final NamedParameterJdbcTemplate template;

    public ProductRepositoryJDBC(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Product save(Product product) {
        if (product.getId() != null) {
            return merge(product);
        }
        return insert(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Product product = template.queryForObject("select * from product where id = :id", Map.of("id", id), getProductRowMapper());
            return Optional.ofNullable(product);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return template.query("select * from product", getProductRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        int updated = template.update("delete from product where id = :id", Map.of("id", id));
        if (updated == 0) {
            throw new ProductNotFoundException("Product not found");
        }
    }

    private Product merge(Product product) {
        SqlParameterSource sqlParameterSource = getSqlParameterSource(product);
        int updated = template.update("update product set product_name = :productName, price = :price, quantity = :quantity where id = :id", sqlParameterSource);
        if (updated == 0) {
            throw new ProductNotFoundException("Product not found");
        }
        return product;
    }

    private Product insert(Product product) {
        SqlParameterSource sqlParameterSource = getSqlParameterSource(product);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into product (product_name, price, quantity) values (:productName, :price, :quantity)";
        template.update(sql, sqlParameterSource, keyHolder);
        fillId(product, keyHolder.getKey().longValue());
        return product;
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product(rs.getString("product_name"), rs.getLong("quantity"), rs.getLong("price"));
            fillId(product, rs.getLong("id"));
            return product;
        };
    }

    private static SqlParameterSource getSqlParameterSource(Product product) {
        return new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("productName", product.getProductName())
                .addValue("price", product.getPrice())
                .addValue("quantity", product.getQuantity());
    }
}

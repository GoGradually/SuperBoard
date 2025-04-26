package jdbc.productmanagementsystem.infra.persistence;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({ProductRepositoryJDBC.class})
class ProductRepositoryJDBCTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("객체 생성 성공")
    void save1() {
        Product product = new Product("haha", 20L, 3000L);

        Product saved = productRepository.save(product);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getProductName()).isEqualTo("haha");
        assertThat(saved.getQuantity()).isEqualTo(20L);
        assertThat(saved.getPrice()).isEqualTo(3000L);
    }

    @Test
    @DisplayName("객체 업데이트 성공")
    void save2() {
        Product product = new Product("haha", 20L, 3000L);
        productRepository.save(product);
        Long beforeId = product.getId();
        System.out.println("beforeId: " + beforeId);

        product.changeName("hehe");
        Product saved = productRepository.save(product);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(beforeId);
        assertThat(saved.getProductName()).isEqualTo("hehe");
        assertThat(saved.getQuantity()).isEqualTo(20L);
        assertThat(saved.getPrice()).isEqualTo(3000L);
    }

    @Test
    @DisplayName("객체 업데이트 실패 - 존재하지 않는 ID")
    void save3() throws NoSuchFieldException, IllegalAccessException {
        Product product = new Product("haha", 20L, 3000L);
        Field id = Product.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(product, 1L);

        assertThrows(ProductNotFoundException.class, () -> productRepository.save(product));
    }

    @Test
    @DisplayName("findById 성공")
    void findById() {
        Product product = new Product("haha", 20L, 3000L);
        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getProductName()).isEqualTo("haha");
        assertThat(found.get().getQuantity()).isEqualTo(20L);
        assertThat(found.get().getPrice()).isEqualTo(3000L);
    }

    @Test
    @DisplayName("빈 리스트 조회")
    void findAll1() {
        List<Product> products = productRepository.findAll();

        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("2개 이상의 객체 조회")
    void findAll2() {
        productRepository.save(new Product("haha", 20L, 3000L));
        productRepository.save(new Product("hoho", 30L, 4000L));

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getProductName()).isEqualTo("haha");
        assertThat(products.get(1).getProductName()).isEqualTo("hoho");
        assertThat(products.get(0).getQuantity()).isEqualTo(20L);
        assertThat(products.get(1).getQuantity()).isEqualTo(30L);
        assertThat(products.get(0).getPrice()).isEqualTo(3000L);
        assertThat(products.get(1).getPrice()).isEqualTo(4000L);
    }

    @Test
    @DisplayName("객체 삭제")
    void deleteById() {
        Product saved = productRepository.save(new Product("haha", 20L, 3000L));

        productRepository.deleteById(saved.getId());

        assertThat(productRepository.findById(saved.getId())).isEmpty();
    }
}
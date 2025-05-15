package jdbc.productmanagementsystem.utils;

import jdbc.productmanagementsystem.domain.model.product.Product;
import org.springframework.test.util.ReflectionTestUtils;

public class TestUtils {

    /**
     * 샘플 Product 객체 반환
     *
     * @return id = 1, 이름 = "hello", 수량 = 20, 가격 = 3000 인 product 객체 반환
     */
    public static Product getSampleProduct() {
        return getProduct(1L, "hello", 20, 3000);
    }

    public static Product getProduct(Long id, String productName, long quantity, long price) {
        Product product = new Product(productName, quantity, price);
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }
}

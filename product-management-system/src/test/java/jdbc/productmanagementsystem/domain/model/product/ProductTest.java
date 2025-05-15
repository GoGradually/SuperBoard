package jdbc.productmanagementsystem.domain.model.product;

import jdbc.productmanagementsystem.domain.exception.InvalidPriceException;
import jdbc.productmanagementsystem.domain.exception.InvalidQuantityException;
import org.junit.jupiter.api.Test;

import static jdbc.productmanagementsystem.utils.TestUtils.getSampleProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void changeName() {
        // given
        Product product = getSampleProduct();

        // when
        product.changeName("hi");

        // then
        assertThat(product.getProductName()).isEqualTo("hi");
    }


    @Test
    void changeQuantity_성공() {
        //given
        Product product = getSampleProduct();

        //when
        product.changeQuantity(30L);


        //then
        assertThat(product.getQuantity()).isEqualTo(30L);

    }

    @Test
    void changeQuantity_실패() {
        //given
        Product product = getSampleProduct();

        //when, then
        assertThatThrownBy(() -> product.changeQuantity(-1L))
                .isInstanceOf(InvalidQuantityException.class)
                .hasMessage("수량은 0개 이상이어야 합니다.");
    }

    @Test
    void changePrice_성공() {
        //given
        Product product = getSampleProduct();

        //when
        product.changePrice(5000L);

        //then
        assertThat(product.getPrice()).isEqualTo(5000L);

    }

    @Test
    void changePrice_실패() {
        //given
        Product product = getSampleProduct();
        //when, then
        assertThatThrownBy(() -> product.changePrice(-3000L))
                .isInstanceOf(InvalidPriceException.class)
                .hasMessage("가격은 0원 이상이어야 합니다.");
    }
}
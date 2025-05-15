package jdbc.productmanagementsystem.application;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static jdbc.productmanagementsystem.utils.TestUtils.getProduct;
import static jdbc.productmanagementsystem.utils.TestUtils.getSampleProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct() {
        // given
        Product product = getSampleProduct();
        when(productRepository.save(product)).thenReturn(product);

        // when
        Product saved = productService.createProduct(product);

        // then
        verify(productRepository).save(product);
        assertNotNull(saved);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getProductName()).isEqualTo(product.getProductName());
        assertThat(saved.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(saved.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void findProductById_성공() {
        // given
        Product product = getSampleProduct();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        // when
        Product findById = productService.findProductById(product.getId());

        // then
        verify(productRepository).findById(product.getId());
        assertNotNull(findById);
        assertThat(findById.getProductName()).isEqualTo(product.getProductName());
        assertThat(findById.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(findById.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void findProductById_존재하지_않는_상품() {
        // given
        Product product = getSampleProduct();
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        // when, then
        assertThatThrownBy(
                () -> productService.findProductById(product.getId()),
                "Product with id %d not found.",
                ProductNotFoundException.class);
    }

    @Test
    void findAllProducts() {
        // given
        Product product1 = getProduct(1L, "hi", 20, 3000);
        Product product2 = getProduct(2L, "hello", 30, 5000);
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        // when
        List<Product> allProducts = productService.findAllProducts();

        // then
        verify(productRepository).findAll();
        assertNotNull(allProducts);
        assertThat(allProducts.size()).isEqualTo(2);
        assertThat(allProducts.get(0).getProductName()).isEqualTo("hi");
        assertThat(allProducts.get(1).getProductName()).isEqualTo("hello");
        assertThat(allProducts.get(0).getQuantity()).isEqualTo(product1.getQuantity());
        assertThat(allProducts.get(1).getQuantity()).isEqualTo(product2.getQuantity());
    }

    @Test
    void changeProfile_성공() {
        // given
        Product product = getSampleProduct();
        Product changedProduct = getProduct(1L, "changed", 30, 5000);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // when
        productService.changeProfile(product.getId(), changedProduct);

        // then
        verify(productRepository).findById(product.getId());
        verify(productRepository).save(captor.capture());

        Product changed = captor.getValue();
        assertNotNull(changed);
        assertThat(changed.getProductName()).isEqualTo(changedProduct.getProductName());
        assertThat(changed.getQuantity()).isEqualTo(changedProduct.getQuantity());
        assertThat(changed.getPrice()).isEqualTo(changedProduct.getPrice());
    }

    @Test
    void changeProfile_존재하지_않는_상품() {
        // given
        Product product = getSampleProduct();
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.changeProfile(product.getId(), product),
                "Product with id %d not found.",
                ProductNotFoundException.class);

    }

    @Test
    void deleteProduct() {
        // given
        Product product = getSampleProduct();
        doNothing().when(productRepository).deleteById(product.getId());

        // when
        productRepository.deleteById(product.getId());

        // then
        verify(productRepository).deleteById(product.getId());
    }
}
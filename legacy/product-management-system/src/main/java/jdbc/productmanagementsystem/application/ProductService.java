package jdbc.productmanagementsystem.application;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    public Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(String.format("Product with id %d not found.", id)));
    }
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product changeProfile(Long id, Product newProduct){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(String.format("Product with id %s not found",id)));
        product.changeName(newProduct.getProductName());
        product.changePrice(newProduct.getPrice());
        product.changeQuantity(newProduct.getQuantity());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}

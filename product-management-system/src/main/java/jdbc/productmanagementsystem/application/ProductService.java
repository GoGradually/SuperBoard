package jdbc.productmanagementsystem.application;

import jdbc.productmanagementsystem.domain.exception.ProductNotFoundException;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

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

    public Product changeName(Long id, String name){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(String.format("Product with id %s not found",id)));
        product.changeName(name);
        return productRepository.save(product);
    }

    public Product changeQuantity(Long id, Long quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(String.format("Product with id %s not found",id)));
        product.changeQuantity(quantity);
        return productRepository.save(product);
    }

    public Product changePrice(Long id, Long price){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(String.format("Product with id %s not found",id)));
        product.changePrice(price);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}

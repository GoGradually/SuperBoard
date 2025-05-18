package jdbc.productmanagementsystem.interfaces.product;

import jdbc.productmanagementsystem.application.ProductService;
import jdbc.productmanagementsystem.domain.model.product.Product;
import jdbc.productmanagementsystem.interfaces.product.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDTO> products = productService.findAllProducts()
                .stream().map(product -> new ProductDTO(
                        product.getId(),
                        product.getProductName(),
                        product.getQuantity(),
                        product.getPrice()))
                .toList();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/new")
    public String newProductPage(Model model) {
        return "newProduct";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Product productById = productService.findProductById(id);
        ProductDTO productDTO = new ProductDTO(productById.getId(), productById.getProductName(), productById.getQuantity(), productById.getPrice());
        model.addAttribute("productDto", productDTO);
        return "editProduct";
    }

    @PostMapping("/new")
    public String saveProduct(ProductDTO productDto) {
        Product product = new Product(productDto.getProductName(), productDto.getQuantity(), productDto.getPrice());
        productService.createProduct(product);
        return "redirect:/";
    }

    @PostMapping("/{id}")
    public String editProduct(@ModelAttribute ProductDTO productDTO, @PathVariable Long id, Model model) {
        Product product = new Product(productDTO.getProductName(), productDTO.getQuantity(), productDTO.getPrice());
        productService.changeProfile(id, product);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}

package jdbc.productmanagementsystem.interfaces.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String productName;
    private Long quantity;
    private Long price;
}

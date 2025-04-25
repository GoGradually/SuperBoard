package jdbc.productmanagementsystem.interfaces.product.dto;

public record ProductDTO(
        Long id,
        String productName,
        Long quantity,
        Long price
) {
}

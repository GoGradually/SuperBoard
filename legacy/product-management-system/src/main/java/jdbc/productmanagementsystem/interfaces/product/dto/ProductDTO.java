package jdbc.productmanagementsystem.interfaces.product.dto;

public class ProductDTO {
    private Long id;
    private String productName;
    private Long quantity;
    private Long price;

    public ProductDTO(Long id, String productName, Long quantity, Long price) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getPrice() {
        return price;
    }
}

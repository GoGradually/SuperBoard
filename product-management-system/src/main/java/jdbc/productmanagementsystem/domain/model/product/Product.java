package jdbc.productmanagementsystem.domain.model.product;

import jdbc.productmanagementsystem.domain.exception.InvalidPriceException;
import jdbc.productmanagementsystem.domain.exception.InvalidQuantityException;
import lombok.Getter;

@Getter
public class Product {
    private Long id;
    private String productName;
    private Long quantity;
    private Long price;

    public Product(String productName, Long quantity, Long price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public void changeName(String name) {
        this.productName = name;
    }

    public void changeQuantity(Long newQuantity) {
        if (newQuantity < 0) {
            throw new InvalidQuantityException("수량은 0개 이상이어야 합니다.");
        }
        this.quantity = newQuantity;
    }

    public void changePrice(Long newPrice) {
        if (newPrice < 0) {
            throw new InvalidPriceException("가격은 0원 이상이어야 합니다.");
        }
        this.price = newPrice;
    }
}

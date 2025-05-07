package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shopping_cart_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id"})})
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shopping_cart_item_id")
    private Long shoppingCartItemId;
    @Column(nullable = false, length = 4)
    private Long itemsCount;
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, insertable = false, updatable = false)
    private LocalDateTime creationDate;
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ShoppingCartItem(Long i, Product product) {
        itemsCount = i;
        this.product = product;
    }

    public Long getShoppingCartItemId() {
        return shoppingCartItemId;
    }

    public void setShoppingCartItemId(Long shoppingCartItemId) {
        this.shoppingCartItemId = shoppingCartItemId;
    }

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return this.itemsCount;
    }

    public Double getTotalPrice() {
        return this.product.getPrice() * this.itemsCount;
    }
}

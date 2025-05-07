package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    public ShoppingCartItem(Long i, Product product) {
        itemsCount = i;
        this.product = product;
    }

    public Long getQuantity() {
        return this.itemsCount;
    }

    public Double getTotalPrice() {
        return this.product.getPrice() * this.itemsCount;
    }
}

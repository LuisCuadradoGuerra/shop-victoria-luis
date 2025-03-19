package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(nullable = false, length = 50)
    private String productName;
    @Column(nullable = false, length = 10)
    private Double price;
    @Column(length = 1000)
    private String productDescription;
    @Column(length = 50)
    private String productIcon;
    @Column(length = 4, nullable = false)
    private Long productStock;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories", //Rename the table of the relation n-m
            joinColumns = {@JoinColumn(name = "product_id")},//Connection with owner side of the relation
            inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false)}) //Connect with the other side of the relation
    private Set<Category> categories;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ShoppingCartItem> shoppingCartItems;


    public Product(String productName, double v, String s, String icon, List<Category> categoryList) {
        this.productName = productName;
        this.price = v;
        this.productDescription = s;
        this.productIcon = icon;
        this.categories = new HashSet<>(categoryList);
    }

    public Product(String productName, double v, String s,List<Category> categoryList) {
        this.productName = productName;
        this.price = v;
        this.productDescription = s;
        this.categories = new HashSet<>(categoryList);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public Long getProductStock() {
        return productStock;
    }

    public void setProductStock(Long productStock) {
        this.productStock = productStock;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }


}

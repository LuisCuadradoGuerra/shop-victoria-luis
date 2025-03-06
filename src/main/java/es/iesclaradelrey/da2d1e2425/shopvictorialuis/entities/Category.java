package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category")
public class Category  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 1000)
    private String categoryDescription;
    @Column(nullable = false, length = 300)
    private String categoryIcon;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER) //MappedBy: Not the proprietary of the relation
    private Set<Product> products;

    public Category(String title, String s, String s1) {
        this.title = title;
        this.categoryDescription = s;
        this.categoryIcon = s1;
    }
}

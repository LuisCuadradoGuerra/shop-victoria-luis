package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="feedback", uniqueConstraints = {@UniqueConstraint(columnNames = {"feedback_id", "username"})})
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="feedback_id")
    private Long feedbackId;
    @Column(nullable = false, length = 2)
    private Double stars;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false,length = 500)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
    private Product product;
//    todo enlazar con users y cambiar username por appUserAlias
}

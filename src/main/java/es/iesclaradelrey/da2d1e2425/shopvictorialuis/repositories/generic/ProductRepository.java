package es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByProductNameContainingIgnoreCaseOrderByProductNameDesc(String name);

    List<Product> findAllByProductIdLessThanEqualOrderByProductIdDesc(Long id);

    List<Product> findAllByCategoriesCategoryId(Long categoriesCategoryId);

    @Query(value = "select count(1) from products p join products_categories pc on p.product_id = pc.product_id where pc.category_id = :id", nativeQuery = true)
    Long countProductsByCategoryId(@Param("id") Long categoryId);

    @Query("select count(p) from Product p join p.categories c where c.categoryId = :id")
    Long countProductsByCategoryIdJPQL(@Param("id") Long categoryId);

    @Query(value = "select AVG(f.stars) from feedback f where f.product_id = :id", nativeQuery = true)
    Optional<Double> averageRatingByProductId(@Param("id") Long productId);

    // Para nuevos productos, el nombre no puede existir.
    Boolean existsByProductName(String productName);

    Boolean existsByProductNameAndProductIdIsNot(String productName, Long productId);
}
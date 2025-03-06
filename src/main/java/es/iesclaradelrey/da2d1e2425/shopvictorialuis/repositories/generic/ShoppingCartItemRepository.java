package es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    @Query(value = "select * from shopping_cart_items sci where sci.product_id = :id", nativeQuery = true)
    Optional<ShoppingCartItem> findByProductId(@Param("id")Long productId);
}

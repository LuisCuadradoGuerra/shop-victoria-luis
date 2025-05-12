package es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    @Query(value = "select * from shopping_cart_items sci where sci.product_id = :product_id and sci.app_user_id = :app_user_id", nativeQuery = true)
    Optional<ShoppingCartItem> findByProductId(@Param("product_id")Long productId,@Param("app_user_id")Long app_user_id);

    void deleteAllByAppUser_AppUserId(Long app_user_id);
    List<ShoppingCartItem> findAllByAppUserAppUserId(Long appUserId);
}

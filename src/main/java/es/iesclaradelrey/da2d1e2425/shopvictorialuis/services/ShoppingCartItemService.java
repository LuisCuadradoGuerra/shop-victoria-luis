package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemService {
    long count();
    void save(ShoppingCartItem trolley);
    List<ShoppingCartItem> findAll();
    Optional<ShoppingCartItem> findById(Long id);
    int addToTrolley(Long productId, Long quantity);
    void removeFromTrolley(Long productId);
    void deleteFromTrolley(Long productId);
    void clearTrolley();
}

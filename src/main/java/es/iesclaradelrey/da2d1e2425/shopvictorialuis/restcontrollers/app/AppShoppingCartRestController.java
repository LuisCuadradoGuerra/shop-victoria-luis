package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppShoppingCartDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ShoppingCartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/v1")
public class AppShoppingCartRestController {


    private final ShoppingCartItemService shoppingCartItemService;

    public AppShoppingCartRestController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }
    
//    todo probando
    @GetMapping("/cart")
    public AppShoppingCartDto getShoppingCart() {
        return shoppingCartItemService.getShoppingCartToApp();
    }

    @PostMapping("/cart/{productId}")
    public List<ShoppingCartItem> addShoppingCart(@PathVariable(name = "productId") Long productId) {
        shoppingCartItemService.addToTrolley(productId, 1L);
        return shoppingCartItemService.findAll();
    }
    @PostMapping("/cart/{productId}/{count}")
   public List<ShoppingCartItem> addShoppingCart(@PathVariable(name = "productId") Long productId, @PathVariable(name = "count") Long count) {
        shoppingCartItemService.addToTrolley(productId,count);
        return shoppingCartItemService.findAll();
    }

    @DeleteMapping("/cart/{productId}")
    public List<ShoppingCartItem> removeShoppingCart(@PathVariable(name = "productId") Long productId) {
        shoppingCartItemService.removeFromTrolley(productId);
        return shoppingCartItemService.findAll();
    }
    @DeleteMapping("/cart")
    public List<ShoppingCartItem> removeShoppingCart() {
        shoppingCartItemService.clearTrolley();
        return shoppingCartItemService.findAll();
    }
}

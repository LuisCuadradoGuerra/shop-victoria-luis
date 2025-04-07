package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppShoppingCartDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ShoppingCartItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/v1/cart")
public class AppShoppingCartRestController {


    private final ShoppingCartItemService shoppingCartItemService;

    public AppShoppingCartRestController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }
    
    @GetMapping({"/", ""})
    public AppShoppingCartDto getShoppingCart() {
        return shoppingCartItemService.getShoppingCartToApp();
    }

//    todo probando
    @PostMapping("/{productId}")
    public AppShoppingCartDto addShoppingCart(@PathVariable(name = "productId") Long productId) {
        shoppingCartItemService.addToTrolley(productId, 1L);
        return shoppingCartItemService.getShoppingCartToApp();
    }
    @PostMapping("/{productId}/{count}")
   public AppShoppingCartDto addShoppingCart(@PathVariable(name = "productId") Long productId, @PathVariable(name = "count") Long count) {
        shoppingCartItemService.addToTrolley(productId,count);
        return shoppingCartItemService.getShoppingCartToApp();
    }

    @DeleteMapping("/{productId}")
    public AppShoppingCartDto removeShoppingCart(@PathVariable(name = "productId") Long productId) {
        shoppingCartItemService.removeFromTrolley(productId);
        return shoppingCartItemService.getShoppingCartToApp();
    }
    @DeleteMapping({"/",""})
    public AppShoppingCartDto removeShoppingCart() {
        shoppingCartItemService.clearTrolley();
        return shoppingCartItemService.getShoppingCartToApp();
    }
}

package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.AddProductToShoppingCartDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ShoppingCartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajax/add-product")
public class ShoppingCartRestController {

    private final ShoppingCartItemService shoppingCartItemService;

    public ShoppingCartRestController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @PostMapping
    public ResponseEntity<Integer> addProductToShoppingCart (@RequestBody AddProductToShoppingCartDto addProductToShoppingCartDto) {
        return ResponseEntity.ok(shoppingCartItemService.addToTrolley(addProductToShoppingCartDto.getProductId(), addProductToShoppingCartDto.getUnits()));
    }
}

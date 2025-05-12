package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController extends BasicController {


    public ShoppingCartController(ShoppingCartItemService shoppingCartItemService) {
        super(shoppingCartItemService);
    }

    @GetMapping("/add-to-trolley/{id}")
    public String addToTrolley(@PathVariable(name = "id") Long productId, @RequestParam(name = "lastUrl") String lastUrl) {
        getShoppingCartItemService().addToTrolley(productId,1L);
        return "redirect:" + lastUrl;
    }

    @GetMapping("/remove-from-trolley/{id}")
    public String removeToTrolley(@PathVariable(name = "id") Long productId, @RequestParam(name = "lastUrl") String lastUrl) {
        getShoppingCartItemService().removeFromTrolley(productId);
        return "redirect:" + lastUrl;
    }

    @GetMapping("/delete-from-trolley/{id}")
    public String deleteToTrolley(@PathVariable(name = "id") Long productId, @RequestParam(name = "lastUrl") String lastUrl) {
        getShoppingCartItemService().deleteFromTrolley(productId);
        return "redirect:" + lastUrl;
    }

    @GetMapping("/clear-trolley")
    public String clearTrolley(@RequestParam(name = "lastUrl") String lastUrl) {
        getShoppingCartItemService().clearTrolley();
        return "redirect:" + lastUrl;
    }
}

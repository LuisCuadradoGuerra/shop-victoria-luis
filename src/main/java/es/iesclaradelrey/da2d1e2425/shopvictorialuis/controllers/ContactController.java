package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController extends BasicController{

    public ContactController(ShoppingCartItemService shoppingCartItemService) {
        super(shoppingCartItemService);
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}

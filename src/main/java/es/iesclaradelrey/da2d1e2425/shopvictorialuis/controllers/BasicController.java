package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class BasicController {
    @Getter(AccessLevel.PACKAGE)
    private final ShoppingCartItemService shoppingCartItemService;


    public BasicController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @ModelAttribute(name = "lastUrl")
    public String getLastUrl(HttpServletRequest request) {
        return request.getRequestURI().replace(request.getContextPath(), "");
    }

    @ModelAttribute
    public void shoppingCartProducts(Model model) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemService.findByAppUserId();
        model.addAttribute("shoppingCartProducts", shoppingCartItems);
        model.addAttribute("shoppingCartProductsCount", shoppingCartItems.stream().mapToLong(ShoppingCartItem::getItemsCount).sum());
        model.addAttribute("shoppingCartProductsPriceTotal", shoppingCartItems.stream()
                .mapToDouble( p -> p.getProduct().getPrice() * p.getItemsCount()).sum());
    }

//todo: Shopping cart dont update in real time
}

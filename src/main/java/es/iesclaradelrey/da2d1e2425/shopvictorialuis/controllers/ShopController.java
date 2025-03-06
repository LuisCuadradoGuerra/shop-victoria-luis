package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Collection;

@Controller
public class ShopController extends BasicController{

    private final CategoryService categoryService;
    private final ProductService productService;

    public ShopController(ShoppingCartItemService shoppingCartItemService, CategoryService categoryService, ProductService productService) {
        super(shoppingCartItemService);
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/shop")
    public ModelAndView shop() {
//        COMO pasar distintos objetos en un mismo MODEL and VIEW
        ModelAndView mav = new ModelAndView();
        Collection<Category> categories = categoryService.findAll();
        mav.addObject("categories", categories);
        Collection<Product> products = productService.findAll();
        mav.addObject("products", products);
        mav.setViewName("shop");

        return mav;
    }
}

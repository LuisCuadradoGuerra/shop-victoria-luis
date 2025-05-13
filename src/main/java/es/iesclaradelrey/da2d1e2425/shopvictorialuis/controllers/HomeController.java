package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class HomeController extends BasicController{

    private final CategoryService categoryService;

    public HomeController(ShoppingCartItemService shoppingCartItemService, CategoryService categoryService) {
        super(shoppingCartItemService);
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public ModelAndView index() {
        Collection<Category> categories = categoryService.findAll();
        return new ModelAndView("index", "categories", categories);
    }

}

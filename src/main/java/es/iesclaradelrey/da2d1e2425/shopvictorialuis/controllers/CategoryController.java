package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Controller
public class CategoryController extends BasicController{

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(ShoppingCartItemService shoppingCartItemService, CategoryService categoryService, ProductService productService) {
        super(shoppingCartItemService);
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/category/{id}")
    public ModelAndView product(@PathVariable(name = "id") Long categoryId) {
        ModelAndView mav = new ModelAndView();
        Optional<Category> category = categoryService.findById(categoryId);

        if (category.isPresent()) {
            mav.addObject("category", category.orElseThrow());
            Collection<Product> products = productService.findByCategoryId(categoryId);
            mav.addObject("products", products);
            mav.setViewName("category");

            ArrayList<Optional<Double>> averages = new ArrayList<>();
            for (Product product : products) {
                Optional<Double> average = productService.averageRatingByProductId(product.getProductId());
                averages.add(average);
            }
            mav.addObject("averages", averages);
            return mav;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
}

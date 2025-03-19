package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Feedback;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController extends BasicController{

    private final ProductService productService;
    private final FeedbackService feedbackService;

    public ProductController(ShoppingCartItemService shoppingCartItemService, ProductService productService, FeedbackService feedbackService) {
        super(shoppingCartItemService);
        this.productService = productService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/product/{id}")
    public ModelAndView products(@PathVariable(name = "id") Long productId) {
        ModelAndView mav = new ModelAndView();
        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            mav.addObject("product", product.orElseThrow());
            mav.setViewName("product");
            List<Feedback> feedbacks = feedbackService.findByProductId(product.orElseThrow().getProductId());
            mav.addObject("feedbacks", feedbacks);
            Optional<Double> average = productService.averageRatingByProductId(product.orElseThrow().getProductId());
            mav.addObject("average", average);
            return mav;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

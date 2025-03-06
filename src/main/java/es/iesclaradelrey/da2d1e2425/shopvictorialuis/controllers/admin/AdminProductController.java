package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.AddProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {


    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/create-new-product")
    public String createNewProduct(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new AddProductDto());
        return "/admin/create-new-product";
    }

    @PostMapping("/create-new-product")
    public String createNewProductSubmit(@Valid @ModelAttribute("product") AddProductDto addProductDto,
                                         BindingResult bindingResult, Model model) {
        List<Category> categories = categoryService.findAll();
        System.out.println(addProductDto);
        model.addAttribute("categories", categories);
        if (bindingResult.hasErrors()) {
            return "/admin/create-new-product";
        }
        System.out.println(addProductDto);
        productService.save(addProductDto);
        return "/admin/create-new-product-ok";
    }


}

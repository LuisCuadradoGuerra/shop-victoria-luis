package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.AddProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {


    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/", ""})
    public String index(@RequestParam(defaultValue = "1") Integer pageNumber,
                        @RequestParam(defaultValue = "5") Integer pageSize,
                        @RequestParam(defaultValue = "productName") String orderAttribute,
                        @RequestParam(defaultValue = "asc") String orderDirection,
                        Model model) {
        model.addAttribute("page", productService.findAll(pageNumber, pageSize, orderAttribute, orderDirection));
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("productName", "Name");
        fields.put("productDescription", "Description");
        fields.put("price", "Price");
        fields.put("productStock", "Stock");
        fields.put("productId", "Product Id");
        model.addAttribute("fields", fields);
        model.addAttribute("orderAttribute", orderAttribute);
        model.addAttribute("orderDirection", orderDirection);
        return "/admin/products/admin-products";
    }

    @GetMapping("/new")
    public String createNewProduct(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new AddProductDto());
        return "/admin/products/admin-products-new";
    }


    @PostMapping("/new")
    public String createNewProductSubmit(@Valid @ModelAttribute("product") AddProductDto addProductDto,
                                         BindingResult bindingResult, Model model) {
        List<Category> categories = categoryService.findAll();
        System.out.println(addProductDto);
        model.addAttribute("categories", categories);
        if (bindingResult.hasErrors()) {
            return "/admin/products/admin-products-new";
        }
        System.out.println(addProductDto);
        productService.save(addProductDto);
        return "/admin/products/admin-products-new-ok";
    }


}

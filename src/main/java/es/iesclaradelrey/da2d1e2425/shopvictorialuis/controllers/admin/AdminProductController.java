package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
    public String createProductForm(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new NewProductDto());
        return "/admin/products/admin-products-new";
    }

    @PostMapping("/new")
    public String createProductSubmit(@Valid @ModelAttribute("product") NewProductDto addProductDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationError", "An error occurred with the validation rule");
            return "/admin/products/admin-products-new";
        }
        productService.save(addProductDto);
        redirectAttributes.addFlashAttribute("successNew", "Product created");
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{productId}")
    public String updateProductForm(@PathVariable(name = "productId") Long productId,
                                    Model model) {
        Product product = productService.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        UpdateProductDto updateProductDto = new UpdateProductDto(
                product.getProductName(),
                product.getProductDescription(),
                product.getProductStock(),
                product.getPrice(),
                product.getCategories()
                        .stream()
                        .map(Category::getCategoryId)
                        .toList());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("updateProductDto", updateProductDto);
        return "/admin/products/admin-products-update";

    }

    @PostMapping("/update/{productId}")
    public String updateProductSubmit(@Valid @ModelAttribute("product") UpdateProductDto updateProductDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @PathVariable(name = "productId") Long productId,
                                      Model model) {
//        todo error update products
        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("validationError", "An error occurred with the validations rules");
            return "/admin/products/admin-products-update";
        }
        try {
            productService.update(updateProductDto, productId);
            redirectAttributes.addFlashAttribute("successUpdate", "Product Updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("unexpectedError", "An unexpected error occurred");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProductForm(@PathVariable(name = "productId") Long productId,
                                    Model model) {
        Product product = productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        model.addAttribute("product", product);
        return "/admin/products/admin-products-delete";
    }

    @PostMapping("/delete/{productId}")
    public String deleteProductSubmit(@PathVariable(name = "productId") Long productId,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        try {
            productService.delete(productId);
            redirectAttributes.addFlashAttribute("successDelete", "Product Deleted");
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorIdProduct", "Product Not Found");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("unexpectedError", "An unexpected error occurred");
        }

        return "redirect:/admin/products";
    }
}

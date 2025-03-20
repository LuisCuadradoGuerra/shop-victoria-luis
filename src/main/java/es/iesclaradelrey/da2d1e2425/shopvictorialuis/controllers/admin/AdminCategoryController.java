package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.CantDeleteCategoryWithProductsException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.CategoryNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/", ""})
    public String index(@RequestParam(defaultValue = "1") Integer pageNumber,
                        @RequestParam(defaultValue = "5") Integer pageSize,
                        @RequestParam(defaultValue = "title") String orderAttribute,
                        @RequestParam(defaultValue = "asc") String orderDirection,
                        Model model) {
        model.addAttribute("page", categoryService.findAll(pageNumber, pageSize, orderAttribute, orderDirection));
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("title", "Title");
        fields.put("categoryDescription", "Description");
        fields.put("categoryId", "Category Id");
        model.addAttribute("fields", fields);
        model.addAttribute("orderAttribute", orderAttribute);
        model.addAttribute("orderDirection", orderDirection);
        return "/admin/categories/admin-categories";
    }

    @GetMapping("/new")
    public String createNewCategory(Model model) {
        model.addAttribute("category", new NewCategoryDto());
        return "/admin/categories/admin-categories-new";
    }

    @PostMapping("/new")
    public String createNewCategory(@Valid @ModelAttribute("category") NewCategoryDto addCategoryDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationError", "An error occurred with the validation rule");
            return "/admin/categories/admin-categories-new";
        }
        categoryService.save(addCategoryDto);
        redirectAttributes.addFlashAttribute("successNew", "Category created");
        return "redirect:/admin/categories";
    }

    @GetMapping("/update/{categoryId}")
    public String updateCategoryForm(@PathVariable(name = "categoryId") Long categoryId,
                                     Model model) {
        Category category = categoryService.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(category.getTitle(), category.getCategoryDescription());
        model.addAttribute("updateCategoryDto", updateCategoryDto);
        return "/admin/categories/admin-categories-update";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategorySubmit(@Valid @ModelAttribute("updateCategoryDto") UpdateCategoryDto updateCategoryDto,
                                       BindingResult bindingResult,
                                       @PathVariable(name = "categoryId") Long categoryId,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/categories/admin-categories-update";
        }
        try{
            categoryService.update(updateCategoryDto, categoryId);
            redirectAttributes.addFlashAttribute("successUpdate", "Category Updated");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("unexpectedError", "An unexpected error occurred");
        }


        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategoryForm(@PathVariable(name = "categoryId") Long categoryId,
                                     Model model) {
        Category category = categoryService.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        model.addAttribute("category", category);
        return "/admin/categories/admin-categories-delete";
    }

    @PostMapping("/delete/{categoryId}")
    public String deleteCategorySubmit(@PathVariable(name = "categoryId") Long categoryId,
                                       RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(categoryId);
            redirectAttributes.addFlashAttribute("successDelete", "The category with id " + categoryId + " has been deleted");
        } catch (CantDeleteCategoryWithProductsException e) {
            redirectAttributes.addFlashAttribute("cascadeError", "You cant delete a category with products");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("unexpectedError", "An unexpected error occurred");
        }
        return "redirect:/admin/categories";
    }
}

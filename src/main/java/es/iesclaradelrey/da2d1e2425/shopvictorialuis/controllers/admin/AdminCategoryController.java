package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.CategoryNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                                    BindingResult bindingResult, Model model) {
        System.out.println(addCategoryDto);
        if (bindingResult.hasErrors()) {
            return "/admin/categories/admin-categories-new";
        }
        System.out.println(addCategoryDto);
        categoryService.save(addCategoryDto);
        return "/admin/categories/admin-categories-new-ok";
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
                                       @PathVariable(name = "categoryId") Long categoryId,
                                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/categories/admin-categories-update";
        }
        categoryService.update(updateCategoryDto, categoryId);
        return "/admin/categories/admin-categories-update-ok";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategoryForm(@PathVariable(name = "categoryId") Long categoryId,
                                     Model model) {
        Category category = categoryService.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        model.addAttribute("category", category);
        return "/admin/categories/admin-categories-delete";
    }

    @PostMapping("/delete/{categoryId}")
    public String deleteCategorySubmit(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return "/admin/categories/admin-categories-delete-ok";
    }
}

package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app/v1/categories")
public class AppCategoryController {

    private final CategoryService categoryService;

    public AppCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/", ""})
    public List<AppCategoryDto> appFindAll() {
        return categoryService.appFindAll();
    }

}

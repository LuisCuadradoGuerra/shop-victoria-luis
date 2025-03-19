package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    long count();
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Page<Category> findAll(Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection);

    void save(NewCategoryDto addCategoryDto);

    void update(UpdateCategoryDto category, Long categoryId);

    void delete(Long categoryId);
}

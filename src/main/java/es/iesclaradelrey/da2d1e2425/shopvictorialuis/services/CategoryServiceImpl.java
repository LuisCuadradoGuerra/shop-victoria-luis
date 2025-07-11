package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppCategoryDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.CantDeleteCategoryWithProductsException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.CategoryNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.NameCategoryAllReadyExistException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<Category> findAll(Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection) {
        Sort.Direction direction = orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(direction, orderAttribute));

        return categoryRepository.findAll(pageRequest);
    }

    @Override
    public void save(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByTitle(newCategoryDto.getTitle())) {
            throw new NameCategoryAllReadyExistException("Name category already exists");
        }

        Category category = new Category(newCategoryDto.getTitle(), newCategoryDto.getCategoryDescription());
        categoryRepository.save(category);

    }

    @Override
    public void update(UpdateCategoryDto updateCategoryDto, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (categoryRepository.existsByTitleAndCategoryIdIsNot(updateCategoryDto.getTitle(), categoryId)) {
            throw new NameCategoryAllReadyExistException("Name category already exists");
        }

        existingCategory.setTitle(updateCategoryDto.getTitle());
        existingCategory.setCategoryDescription(updateCategoryDto.getCategoryDescription());

        categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        if (!category.getProducts().isEmpty()) {
            throw new CantDeleteCategoryWithProductsException("You can't delete a category with products");
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<AppCategoryDto> appFindAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, AppCategoryDto.class))
                .collect(Collectors.toList());
    }
}

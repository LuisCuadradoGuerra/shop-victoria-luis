package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppFindProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.NameProductAllReadyExistException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.CategoryRepository;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.FeedbackRepository;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
//    todo: I change a repo method findByProductId. Maybe this crash at some point

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoriesCategoryId(categoryId);
    }

    @Override
    public Page<Product> findAll(Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection) {
        Sort.Direction direction = orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(direction, orderAttribute));

        return productRepository.findAll(pageRequest);
    }

    @Override
    public Page<AppFindProductDto> customSearch(String search, Long cat, Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection) {
        Sort.Direction direction = orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(direction, orderAttribute));

        Page<Product> foundProducts;

        if (cat == null && search != null) {
            foundProducts = productRepository.findAllByProductNameContainingIgnoreCase(search, pageRequest);
        } else if (search == null && cat != null) {
            foundProducts = productRepository.findAllByCategoriesCategoryId(cat , pageRequest);
        } else if (cat != null && search != null) {
            foundProducts = productRepository.findAllByProductNameContainingIgnoreCaseAndCategoriesCategoryId(search, cat, pageRequest);
        } else {
            foundProducts = productRepository.findAll(pageRequest);
        }

        return new PageImpl<>(
                foundProducts
                        .stream()
                        .map(product -> modelMapper.map(product, AppFindProductDto.class))
                        .collect(Collectors.toList()),
                pageRequest, foundProducts.getSize());
    }

//    Sql and JPQL

    @Override
    public Long countProductsByCategoryId(Long categoryId) {
        return productRepository.countProductsByCategoryId(categoryId);
    }

    @Override
    public Long countProductsByCategoryIdJPQL(Long categoryId) {
        return productRepository.countProductsByCategoryId(categoryId);
    }

    @Override
    public Optional<Double> averageRatingByProductId(Long productId) {
        return productRepository.averageRatingByProductId(productId);
    }

    @Override
    public void save(NewProductDto newProductDto) {
        if (productRepository.existsByProductName(newProductDto.getProductName())) {
            throw new NameProductAllReadyExistException("This product name is already in use");
        }
//        Que NO hacer
//        if (!productRepository.findAll().stream().anyMatch(product -> product.getProductName().equals(newProductDto.getProductName()))) {


        List<Category> categories = categoryRepository.findAllById(newProductDto.getProductCategoriesIds());
        Product product = new Product(newProductDto.getProductName(), newProductDto.getProductPrice(), newProductDto.getProductDescription(), categories);
        product.setProductStock(newProductDto.getProductStock());
        productRepository.save(product);


    }

    @Override
    public void update(UpdateProductDto updateProductDto, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (productRepository.existsByProductNameAndProductIdIsNot(updateProductDto.getProductName(), productId)) {
            throw new NameProductAllReadyExistException("This product name is already in use");
        }


        existingProduct.setProductName(updateProductDto.getProductName());
        existingProduct.setProductDescription(updateProductDto.getProductDescription());
        existingProduct.setPrice(updateProductDto.getProductPrice());
        existingProduct.setProductStock(updateProductDto.getProductStock());

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(updateProductDto.getProductCategoriesIds()));
        existingProduct.setCategories(categories);

        productRepository.save(existingProduct);


    }

    @Override
    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
        feedbackRepository.deleteAll(product.getFeedbacks());
        productRepository.delete(product);
    }

    private Specification<Product> getProductSpecification(String search, Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();




            return predicate;
        };
    }

}

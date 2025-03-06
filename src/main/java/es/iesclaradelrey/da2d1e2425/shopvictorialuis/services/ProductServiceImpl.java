package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.AddProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.CategoryRepository;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    public Optional<Product> findByProductId(Long id) {
        return productRepository.findByProductId(id);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoriesCategoryId(categoryId);
    }

//    Sql and JPQL

    @Override
    public Long countProductsByCategoryId(Long categoryId){
        return productRepository.countProductsByCategoryId(categoryId);
    }

    @Override
    public Long countProductsByCategoryIdJPQL(Long categoryId){
        return productRepository.countProductsByCategoryId(categoryId);
    }

    @Override
    public Optional<Double> averageRatingByProductId(Long productId){
        return productRepository.averageRatingByProductId(productId);
    }

    @Override
    public void save(AddProductDto addProductDto) {
        List<Category> categories = categoryRepository.findAllById(addProductDto.getProductCategoriesIds());
        Product product = new Product(addProductDto.getProductName(), addProductDto.getProductPrice(), addProductDto.getProductDescription(), categories);
        product.setProductStock(addProductDto.getProductStock());
        productRepository.save(product);
    }
}

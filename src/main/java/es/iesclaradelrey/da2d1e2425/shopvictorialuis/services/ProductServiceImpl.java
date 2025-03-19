package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.CategoryRepository;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public void save(NewProductDto addProductDto) {
        List<Category> categories = categoryRepository.findAllById(addProductDto.getProductCategoriesIds());
        Product product = new Product(addProductDto.getProductName(), addProductDto.getProductPrice(), addProductDto.getProductDescription(), categories);
        product.setProductStock(addProductDto.getProductStock());
        productRepository.save(product);
    }

    @Override
    public void update(UpdateProductDto updateProductDto, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

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
        //todo: delete feedbacks related with the product
        productRepository.delete(product);
    }


}

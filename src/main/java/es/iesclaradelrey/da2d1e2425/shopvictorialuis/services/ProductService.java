package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.NewProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin.UpdateProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppFindProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    long count();
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findByCategoryId(Long categoryId);
    Page<Product> findAll(Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection);
    Page<AppFindProductDto> customSearch(String search, Long cat, Integer pageNumber, Integer pageSize, String orderAttribute, String orderDirection);

//    Consultas sql y jpql

    Long countProductsByCategoryId(Long categoryId);

    Long countProductsByCategoryIdJPQL(Long categoryId);

    Optional<Double> averageRatingByProductId(Long productId);
    
    void save(NewProductDto addProductDto);

    void update(UpdateProductDto product, Long productId);

    void delete(Long productId);
}

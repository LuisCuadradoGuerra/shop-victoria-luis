package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.AddProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    long count();
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findByProductId(Long id);
    List<Product> findByCategoryId(Long categoryId);


//    Consultas sql y jpql

    Long countProductsByCategoryId(Long categoryId);

    Long countProductsByCategoryIdJPQL(Long categoryId);

    Optional<Double> averageRatingByProductId(Long productId);

    void save(AddProductDto addProductDto);
}

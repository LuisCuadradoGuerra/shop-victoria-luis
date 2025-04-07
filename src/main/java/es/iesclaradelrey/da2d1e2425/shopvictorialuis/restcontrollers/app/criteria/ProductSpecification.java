package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app.criteria;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> findProductsForApp(String search, Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.conjunction();

            if (search != null && !search.isBlank()) {
                // Create Or predicate for productName and productDescription
                Predicate nameOrDescritionPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("productName"), String.format("%%%s%%", search)),
                        criteriaBuilder.like(root.get("productDescription"), String.format("%%%s%%", search)));

                // Add to finalPredicate with And condition
                finalPredicate = criteriaBuilder.and(finalPredicate, nameOrDescritionPredicate);
            }

            if (categoryId != null) {
                Join<Product, Category> productCategoryJoin = root.join("categories", JoinType.INNER); // The relation is called "categories"
                Predicate categoryPredicate = criteriaBuilder.equal(productCategoryJoin.get("categoryId"), categoryId);

                // Add to finalPredicate with And condition
                finalPredicate = criteriaBuilder.and(finalPredicate, categoryPredicate);
            }

            return finalPredicate;
        };
    }
}

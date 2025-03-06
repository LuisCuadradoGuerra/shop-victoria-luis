package es.iesclaradelrey.da2d1e2425.shopvictorialuis.commandLineRunner;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class TestCommandLineRunner implements CommandLineRunner {

    private final ProductService productService;

    public TestCommandLineRunner(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner is running...");
        System.out.println("Testing sql sentences");
        System.out.println(productService.countProductsByCategoryId(1L));

        System.out.println("Testing jpql sentences");
        System.out.println(productService.countProductsByCategoryIdJPQL(1L));

        System.out.println("Testing sql sentences to implement");

        System.out.println(productService.averageRatingByProductId(3L));
    }
}

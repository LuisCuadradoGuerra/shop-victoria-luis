package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppFindProductDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/v1/products")
public class AppProductController {

    private final ProductService productService;

    public AppProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/find")
    public ResponseEntity<Page<AppFindProductDto>> productsFinder(@RequestParam(defaultValue = "") String search,
                                                  @RequestParam(defaultValue = "") Long cat,
                                                  @RequestParam(defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "productName") String orderAttribute,
                                                  @RequestParam(defaultValue = "asc") String orderDirection,
                                                  Model model) {
        return ResponseEntity.ok(productService.customSearch(search,cat,pageNumber, pageSize, orderAttribute, orderDirection));
    }
}

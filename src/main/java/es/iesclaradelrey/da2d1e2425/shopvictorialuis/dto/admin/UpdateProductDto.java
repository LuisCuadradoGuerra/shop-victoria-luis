package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateProductDto {
    @NotNull
    @NotBlank(message = "Product Name can't be blank")
    private String productName;
    private Long productId;
    private String productDescription;
    @NotNull
    @NotEmpty(message = "Your product need at least one categories")
    private List<Long> productCategoriesIds;
    @NotNull(message = "The product need a stock")
    @Positive(message = "If there is no stock, don't put it on sale")
    private Long productStock;
    @NotNull(message = "The product need price")
    @Positive(message = "Do you want earn money, right?")
    private Double productPrice;

    public UpdateProductDto() {
    }

    public UpdateProductDto(String productName, String productDescription, Long productStock, Double productPrice, List<Long> productCategoriesIds) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productStock = productStock;
        this.productPrice = productPrice;
        this.productCategoriesIds = productCategoriesIds;
    }
}
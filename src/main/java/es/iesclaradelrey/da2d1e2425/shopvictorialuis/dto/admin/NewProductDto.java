package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewProductDto {
    @NotNull
    @NotBlank(message = "Product Name can't be blank")
    private String productName;
    @NotNull
    @NotBlank(message = "Product Description can't be blank")
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

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)   FECHAS, no para fecha y hora
//    @DateTimeFormat(pattern = "HH:mm")
//    @DateTimeFormat(pattern = "yyy-MM-dd'T'HH:mm")
//    @Email
//    @Past  para coger fechas del pasado

}

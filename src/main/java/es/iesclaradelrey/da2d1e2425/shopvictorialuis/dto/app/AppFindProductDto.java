package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppFindProductDto {
    @NotNull
    @Positive(message = "The id can't be negative")
    private Long productId;
    @NotNull
    @NotBlank(message = "The product name can't be blank")
    private String productName;
    @NotBlank(message = "The product must have a description")
    private String productDescription;
    @NotNull
    @Positive(message = "You can't put a negative price!")
    private Double price;
    @NotBlank(message = "You need a icon for your product mate")
    private String productIcon;
}

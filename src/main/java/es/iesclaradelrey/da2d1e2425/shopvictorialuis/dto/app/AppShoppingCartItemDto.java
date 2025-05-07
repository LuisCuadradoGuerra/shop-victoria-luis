package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppShoppingCartItemDto {

    @NotNull
    @Positive(message = "The id of the product can't be negative")
    private Integer productId;
    @NotBlank(message = "The product name can't be blank")
    private String productName;
    @NotNull
    @Positive(message = "You don't want to be poor, you're human not an ONG")
    private Double productPrice;
    @NotNull
    @Positive(message = "If you don't have at least one product, why are you here?")
    private Long quantity;
    @NotBlank(message = "You should put an image of the product")
    private String productIcon;
    @NotNull
    @Positive(message = "You need to pay for your products")
    private Double totalPrice;

}

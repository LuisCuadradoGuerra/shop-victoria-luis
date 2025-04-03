package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
//import org.springframework.security.core.parameters.P;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppShoppingCartDto {

    @NotNull
    @Positive(message = "You need to buy something, please...")
    private Long productsCount;
    @NotNull
    @Positive(message = "Pay!!")
    private Double productPriceCount;
    @NotNull
    private List<AppShoppingCartItemDto> products;

}

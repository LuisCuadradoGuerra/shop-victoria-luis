package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToShoppingCartDto {
    private Long productId;
    private Long units;
}

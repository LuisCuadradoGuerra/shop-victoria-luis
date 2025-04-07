package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppCategoryDto {
    @NotNull
    @Positive
    private Long categoryId;
    @NotNull
    @NotBlank(message = "Category title can't be blank")
    private String title;
    @NotNull
    @NotBlank(message = "The category needs a description")
    private String categoryDescription;
    @NotNull
    @NotBlank(message = "You should put an icon")
    private String categoryIcon;
}

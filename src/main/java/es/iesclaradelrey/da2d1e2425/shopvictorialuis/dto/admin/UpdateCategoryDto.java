package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateCategoryDto {

    @NotNull
    @NotBlank(message = "Category title can't be blank")
    private String title;
    @NotNull
    @NotBlank(message = "The category needs a description")
    private String categoryDescription;

    public UpdateCategoryDto() {
    }

    public UpdateCategoryDto(String title, String categoryDescription) {
        this.title = title;
        this.categoryDescription = categoryDescription;
    }
}
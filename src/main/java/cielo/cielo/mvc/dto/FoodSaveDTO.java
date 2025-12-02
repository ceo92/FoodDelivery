package cielo.cielo.mvc.dto;

import cielo.cielo.domain.Food;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FoodSaveDTO(@NotBlank String name, String image, @NotNull Integer price) {
  public Food toEntity(){
    return Food.builder().name(name()).image(image()).price(price()).build();
  }
}


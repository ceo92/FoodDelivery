package cielo.cielo.mvc.dto;

import cielo.cielo.domain.Food;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FoodSaveDTO(@NotBlank String name, MultipartFile image, @NotNull Integer price) {
  public Food toEntity(UploadFile uploadFile){
    return Food.builder().name(name()).image(uploadFile).price(price()).build();
  }
}


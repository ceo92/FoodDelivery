package cielo.cielo.mvc.dto;

import lombok.Builder;

@Builder
public record FoodResponseDTO(Long id, String name, UploadFile image, int price){}

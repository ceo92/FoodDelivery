package cielo.cielo.mvc.service;

import cielo.cielo.domain.Food;
import cielo.cielo.mvc.dto.FoodResponseDTO;
import cielo.cielo.mvc.dto.FoodSaveDTO;
import cielo.cielo.mvc.dto.FoodUpdateDTO;
import cielo.cielo.mvc.repository.FoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

  private final FoodRepository foodRepository;
  @Transactional
  public Long save(FoodSaveDTO foodSaveDTO) {
    Food food = foodSaveDTO.toEntity();
    return foodRepository.save(food);
  }

  @Transactional
  public void update(Long id, FoodUpdateDTO foodUpdateDTO) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("지정된 id에 대한 음식이 존재하지 않습니다."));
    // JPA 변경 감지 = 더티 체킹
    food.updateFood(foodUpdateDTO);
  }

  public FoodResponseDTO findOne(Long id){
    return foodRepository.findById(id).map(
            food -> FoodResponseDTO.builder().id(food.getId()).name(food.getName())
                .price(food.getPrice()).image(food.getImage()).build())
        .orElseThrow(() -> new IllegalArgumentException("지정된 id에 대한 음식이 존재하지 않습니다."));
  }

  public List<FoodResponseDTO> findAll(){
    return foodRepository.findAll().stream().map(
        food -> FoodResponseDTO.builder().id(food.getId()).name(food.getName())
            .price(food.getPrice()).image(food.getImage()).build()).toList();
  }
  @Transactional
  public void delete(Long id){
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("지정된 id에 대한 음식이 존재하지 않습니다."));
    foodRepository.delete(food);
  }

}

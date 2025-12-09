package cielo.cielo.mvc.controller;

import cielo.cielo.domain.Food;
import cielo.cielo.mvc.dto.FoodSaveDTO;
import cielo.cielo.mvc.dto.FoodUpdateDTO;
import cielo.cielo.mvc.service.FoodService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("foods")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;
  @Value("${file.dir}")
  private String fileDir;
  @GetMapping
  public String getFoods(Model model){
    model.addAttribute("foods", foodService.findAll());
    return "food/foods";
  }

  @GetMapping("/{id}")
  public String getFood(@PathVariable("id") Long id, Model model){
    model.addAttribute("food", foodService.findOne(id));
    return "food/food";
  }

  @GetMapping("/save")
  public String saveForm(Model model) {
    model.addAttribute("food", Food.builder().build());
    return "food/saveForm";
  }

  @PostMapping("/save")
  public String saveForm(@Validated @ModelAttribute("food") FoodSaveDTO foodSaveDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
    // 이게 저장 끝임 ㅋㅋ ㅈㄴ간단하네 ;;
    String originalFileName = foodSaveDTO.image().getOriginalFilename();
    int delimiter = originalFileName.lastIndexOf(".");
    String onlyFileName = originalFileName.substring(0, delimiter);
    String extension = originalFileName.substring(delimiter, originalFileName.length()); // 확장자

    String storeFileName = UUID.randomUUID() + extension;


    String storageStoreFileName = fileDir + storeFileName;
    foodSaveDTO.image().transferTo(new File(storageStoreFileName)); //스토리지(내 기준 로컬)에 저장
    Long saveId = foodService.save(foodSaveDTO); // DB에 파일명 저장
    redirectAttributes.addAttribute("id", saveId);
    redirectAttributes.addAttribute("saveCk", true);
    return "redirect:/foods/{id}";
  }

  @GetMapping("/{id}/update")
  public String updateForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute("food", foodService.findOne(id));
    return "food/updateForm";
  }
  @PostMapping("/{id}/update")
  public String updateForm(@Validated @ModelAttribute("food") FoodUpdateDTO foodUpdateDTO, @PathVariable("id") Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes){
    foodService.update(id, foodUpdateDTO);
    redirectAttributes.addAttribute("id", id);
    redirectAttributes.addAttribute("updateCk", true);
    return "redirect:/foods/{id}";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id){
    foodService.delete(id);
    return "redirect:/foods";
  }

  // API: 수정 시 post 대신 put 저장만 post 왜냐하면 post는 멱등하지 않지만 put은 멱등하기 때문 100번 시도해도 결과 같음 하지만 post는 달라질수있음


}

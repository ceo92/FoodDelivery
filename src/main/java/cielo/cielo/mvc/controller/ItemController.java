package cielo.cielo.mvc.controller;

import cielo.cielo.domain.Book;
import cielo.cielo.mvc.dto.BookSaveDTO;
import cielo.cielo.mvc.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/save")
  public String saveItem(Model model){
    model.addAttribute("item", new Book());
    return "item/saveForm";
  }

  @PostMapping("/save")
  public String saveItem(@ModelAttribute("item") BookSaveDTO bookSaveDTO) {
    itemService.saveItem(bookSaveDTO);
    return "redirect:/";
  }

  @GetMapping
  public String getItems(){
    return "item/items";
  }
}

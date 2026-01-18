package cielo.cielo.mvc.service;

import cielo.cielo.domain.Item;
import cielo.cielo.mvc.dto.BookSaveDTO;
import cielo.cielo.mvc.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public Long saveItem(BookSaveDTO bookSaveDTO){
    Item item = bookSaveDTO.toEntity();
    itemRepository.save(item);
    return item.getId();
  }

  public Item findOne(Long id){
    return itemRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 id입니다."));
  }

  public List<Item> findAll(){
    return itemRepository.findAll();
  }

}

package cielo.cielo.mvc.repository;

import cielo.cielo.domain.Item;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item) {
    em.persist(item);
  }

  public Optional<Item> findById(Long id){
    return Optional.ofNullable(em.find(Item.class, id));
  }

  public List<Item> findAll(){
    return em.createQuery("select i from Item i", Item.class).getResultList();
  }



}

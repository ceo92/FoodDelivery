package cielo.cielo.mvc.repository;

import cielo.cielo.domain.Food;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodRepository {
  private final EntityManager em;
  public Long save(Food food){
    em.persist(food);
    return food.getId();
  }

  public Optional<Food> findById(Long id){
    return Optional.ofNullable(em.find(Food.class, id));
  }

  public List<Food> findAll(){
    return em.createQuery("SELECT f FROM Food f", Food.class).getResultList();
  }

  public void delete(Food food){
    em.remove(food);
  }

}

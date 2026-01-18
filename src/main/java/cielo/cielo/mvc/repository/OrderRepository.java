package cielo.cielo.mvc.repository;

import cielo.cielo.api.SelectOrdersDTO;
import cielo.cielo.domain.Order;
import cielo.cielo.mvc.enumerate.OrderStatus;
import cielo.cielo.mvc.dto.OrderSearch;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private final EntityManager em;

  public void save(Order order){
    em.persist(order);
  }


  public Optional<Order> findById(Long id){
    return Optional.ofNullable(em.find(Order.class, id));
  }


  public List<Order> findAllByNameAndStatus(OrderSearch orderSearch){
    String name = orderSearch.getMemberName();
    OrderStatus orderStatus = orderSearch.getOrderStatus();
    if(StringUtils.hasText(name) && orderStatus != null){
      return em.createQuery("select o from Order o inner join o.member m where m.name like concat(%, :name, %) and o.orderStatus = :orderStatus", Order.class)
          .setParameter("name", name)
          .setParameter("orderStatus", orderStatus)
          .getResultList();
    }
    else if(StringUtils.hasText(name) && orderStatus == null){
      return em.createQuery("select o from Order o inner join o.member m where m.name like concat(%, :name, %)", Order.class)
          .setParameter("name", name)
          .getResultList();
    }
    else if(!StringUtils.hasText(name) && orderStatus != null){
      return em.createQuery("select o from Order o where o.orderStatus = :orderStatus", Order.class)
          .setParameter("orderStatus", orderStatus)
          .getResultList();
    }
    else{
      return em.createQuery("select o from Order o", Order.class).getResultList();
    }

  }


  public List<Order> findAllByDeliveryAndMember(){
    return em.createQuery("select o from Order o"
        + "inner join fetch o.member m"
        + "inner join fetch o.delivery d", Order.class).getResultList();
  }
  public List<SelectOrdersDTO> findAllByDTO(){
    return em.createQuery("select new cielo.cielo.api.SelectOrdersDTO(o.id, m.name, o.orderDate, o.orderStatus, d.address) "
        + "from Order o "
        + "join o.member m "
        + "join o.delivery d ", SelectOrdersDTO.class).getResultList();
  }







}

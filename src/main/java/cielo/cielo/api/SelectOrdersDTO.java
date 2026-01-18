package cielo.cielo.api;

import cielo.cielo.domain.Address;
import cielo.cielo.domain.Order;
import cielo.cielo.mvc.enumerate.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectOrdersDTO {
  private Long orderId;
  private String memberName;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  public SelectOrdersDTO(Order o){
    orderId = o.getId();
    memberName = o.getMember().getName(); //LAZY
    orderDate = o.getOrderDate();
    orderStatus = o.getOrderStatus();
    address = o.getDelivery().getAddress(); //LAZY
  }


}

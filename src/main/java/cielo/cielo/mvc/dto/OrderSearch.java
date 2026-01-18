package cielo.cielo.mvc.dto;

import cielo.cielo.mvc.enumerate.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderSearch {

  private String memberName;
  private OrderStatus orderStatus;


}

package cielo.cielo.api;

import cielo.cielo.domain.Order;
import cielo.cielo.mvc.dto.OrderSearch;
import cielo.cielo.mvc.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {

  private final OrderService orderService;



  // 1. DTO 변환 후 리턴
  // 이렇게 DTO로만 조인하면 N+1문제 발생, ORDER와 연관관계인 MEMBER, DELIVERY 연관관계 객체를 로딩하면서 N번의 쿼리가 추가로 나감, 당연하겠지만 로딩하는 member, delivery가 서로 다른 id값이면 또 추가로 n번의 쿼리가 나가서 1+n+N 문제 발생
  @GetMapping("/api/orders/v1")
  public Result ordersV1(){
    List<Order> orders = orderService.findOrders(new OrderSearch());
    List<SelectOrdersDTO> orderDtos = orders.stream().map(SelectOrdersDTO::new).toList();
    return new Result(orderDtos);
  }

  // 2. DTO 변환 후 리턴 + fetch join
  @GetMapping("/api/orders/v2")
  public Result ordersV2(){
    List<Order> orders = orderService.findOrdersByFetchJoin();
    List<SelectOrdersDTO> orderDtos = orders.stream().map(SelectOrdersDTO::new).toList();
    return new Result(orderDtos);
  }

  // 3. 처음부터 DTO로 조회
  @GetMapping("/api/orders/v3")
  public Result ordersV3(){
    List<SelectOrdersDTO> orderDtos = orderService.findOrdersByDto();
    return new Result(orderDtos);
  }

  @Data
  @AllArgsConstructor
  static class Result<T>{
    T data;
  }
}

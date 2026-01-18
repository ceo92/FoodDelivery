package cielo.cielo.mvc.service;

import cielo.cielo.api.SelectOrdersDTO;
import cielo.cielo.domain.Delivery;
import cielo.cielo.domain.Item;
import cielo.cielo.domain.Member;
import cielo.cielo.domain.Order;
import cielo.cielo.domain.OrderItem;
import cielo.cielo.mvc.enumerate.DeliveryStatus;
import cielo.cielo.mvc.dto.OrderSaveDto;
import cielo.cielo.mvc.dto.OrderSearch;
import cielo.cielo.mvc.repository.ItemRepository;
import cielo.cielo.mvc.repository.MemberRepository;
import cielo.cielo.mvc.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  // 1. 주문 생성 2. 주문 검색 3. 주문  취소
  public Long save(OrderSaveDto orderSaveDto){
    Long memberId = orderSaveDto.getMemberId();
    Long itemId = orderSaveDto.getItemId();
    int orderCount = orderSaveDto.getOrderCount();

    Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("해당 이름의 회원이 없습니다"));
    Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("해당 이름의 상품이 존재하지 않습니다."));
    // 배송정보 생성: 회원 주소 할당 + 배송 상태 준비중(물론 FM대로 하면 입력받은 주소값으로 해야하지만 샘플이니 회원의 실거주지에서 가져오기)
    Delivery delivery = new Delivery();
    delivery.setDeliveryStatus(DeliveryStatus.READY);
    delivery.setAddress(member.getAddress());
    Integer orderPrice = item.getPrice();
    // 일단 요구사항적으로 한 주문에 한 상품만 가능한듯
    OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, orderCount);
    Order order = Order.createOrder(member, delivery, orderItem);
    orderRepository.save(order); // Delivery, OrderItem은 별도로 조회해오지 않고 여기서 생성한 거여서 persist를 해줘야 영속성 컨텍스트에 관리가되면서 저장이 되지만, Order의 Cascade 옵션을 통해 연쇄적으로 같이 저장이 되는것
    return order.getId();



  }

  // 주문 단 건 조회

  // 전체 주문 조회
  public List<Order> findOrders(OrderSearch orderSearch){
    return orderRepository.findAllByNameAndStatus(orderSearch);
  }

  public List<Order> findOrdersByFetchJoin(){
    return orderRepository.findAllByDeliveryAndMember();
  }

    public List<SelectOrdersDTO> findOrdersByDto(){
    return orderRepository.findAllByDTO();
  }





  public void cancel(Long id){
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("존재하지 않는 id입니다."));
    order.cancel();
  }

}

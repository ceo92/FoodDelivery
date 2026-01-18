package cielo.cielo.domain;

import cielo.cielo.mvc.enumerate.DeliveryStatus;
import cielo.cielo.mvc.enumerate.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
@Getter @Setter(AccessLevel.PRIVATE)
public class Order {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Setter // 단방향 연관관계 설정 메서드
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id") // 이때 외래키를 지정해주는 어노테이션이니 member_id를 쓰는게 맞겠지 ㅇㅇ? 즉 Order 테이블 안에 Member 외래키를 갖고 있는 것임 ㅇㅇ Member은 아니고
  private Member member; //Order 테이블이 연관관계 주인이므로 member_id 외래키값을 갖고 대신 Member은 외래키를 안 가짐, DB에선 보통 두 테이블 중 하나만 외래키를 가짐. 그게 다대일 연관관계에서 다 쪽에서 외래키를 갖는 것임. 주로 다 쪽에서의 CRUD가 발생하고, 일쪽에서 만약 필요하다면 단순 조회저정도? 근데 그 마저도 잘 안 쓰임

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus;

  @DateTimeFormat(pattern = "yyyy/mm/dd")
  private LocalDateTime orderDate;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;


  // 정적 팩토리 메서드: 복잡한 연관관계 + 정해진 값으로 필드 설정
  public static Order createOrder(Member member, Delivery delivery, OrderItem ... orderItems){
    Order order = new Order();
    order.addMember(member);
    order.addDelivery(delivery);
    Arrays.stream(orderItems).forEach(orderItem -> order.addOrderItem(orderItem));
    order.setOrderDate(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.ORDER);
    return order;
  }

  /**
   * 비즈니스 로직
   */

  public void cancel(){
    DeliveryStatus deliveryStatus = delivery.getStatus();
    if(deliveryStatus == DeliveryStatus.COMP){
      throw new IllegalStateException("배송이 시작된 상품은 취소가 불가능합니다.");
    }
    setOrderStatus(OrderStatus.CANCEL);
    orderItems.stream().forEach(orderItem -> orderItem.cancel());

  }

  public int getTotalPrice(){
    return this.orderItems.stream().mapToInt(OrderItem::countOrderPrice).sum();
  }

  /**
   * 연관관계 편의 메서드
   */
  public void addMember(Member member){
    this.member = member;
    member.getOrders().add(this);
  }

  public void addDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  public void addOrderItem(OrderItem orderItem){
    orderItem.setOrder(this);
    this.orderItems.add(orderItem);
  }

  // 이건 맞지 않음,
/*  public void addOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    orderItems.forEach(orderItem -> orderItem.setOrder(this));
  }*/







}

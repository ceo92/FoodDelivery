package cielo.cielo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;

  private Integer orderPrice;

  private Integer count;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  @Setter
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  /**
   * 연관관계 편의 메서드 양방향 ㅇㅇ
   */
  //  연관관계 편의 메서드는 다나 일이나 둘 다 어디든 정의해도 상관없음, 로직적으로 잘 활용할 수 있는 곳에 정의하도록 하자!
/*  public void addOrder(Order order){
    this.order = order;
    order.getOrderItems().add(this);
  }*/

  /**
   * 비즈니스 로직: 주문
   */
  public static OrderItem createOrderItem(Item item, Integer orderPrice, Integer count){
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);
    item.removeStock(count);
    return orderItem;
  }


  public int countOrderPrice(){
    return getOrderPrice() * getCount();
  }
  // OrderItem 의 주문 취소: 다시 기존 수량을 복구해주는 역할, Item과 연관관계 맺고있기 때문
  public void cancel() {
    item.addStock(count); //다시 수량 복구
  }

  // 주문: 특정 상품의 가격 * 수량
/*  public void order(Item item, int purchaseQuantity){
    OrderItem orderItem = new OrderItem();
    this.orderPrice = item.getPrice() * purchaseQuantity;
    Order order = new Order();
    order.changeOrderStatus(OrderStatus.ORDER);

    item.removeStock(purchaseQuantity);
  }*/





}

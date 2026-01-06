package cielo.cielo.domain;

import cielo.cielo.enumerate.OrderStatus;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
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

  private LocalDateTime orderDate;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;


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

}

package cielo.cielo.domain;

import cielo.cielo.enumerate.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// 한 배송번호는 한 주문만 식별하고, 그것은 한 회원에게만 종속됨, 즉 주문과 회원 각각 OneToOne이 맞음
@Entity
@Getter
public class Delivery {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long id;

  @Embedded
  private Address address;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  @Setter
  private Order order;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

}
